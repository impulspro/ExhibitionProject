package com.exhibit.controller.commands.impl;

import com.exhibit.controller.commands.Command;
import com.exhibit.dao.ExhibitionDao;
import com.exhibit.exeptions.DaoException;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Hall;
import com.exhibit.services.ExhibitionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.exhibit.util.UtilConstants.*;


public class GetExhibitions implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    List<Exhibition> exhList;

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {

        HttpSession session = req.getSession();

        String sortType;
        if (req.getParameter(SORT_TYPE) != null) {
            sortType = req.getParameter(SORT_TYPE);
            session.setAttribute(SORT_TYPE, sortType);
        } else {
            sortType = (String) session.getAttribute(SORT_TYPE);
        }

        try {
            exhList = new ExhibitionDao().findAll();
            switch (sortType) {
                case "sortByDate":
                    if (!sortByDate(req.getParameter(EXH_DATE))) {
                        req.getSession().setAttribute(ERROR_MESSAGE, "no exhibition on this date");
                    }
                    break;
                case "sortByTheme":
                    Comparator<Exhibition> comparatorT
                            = Comparator.comparing(Exhibition::getTheme);
                    exhList.sort(comparatorT);
                    break;
                case "sortByPrice":
                    Comparator<Exhibition> comparatorP
                            = Comparator.comparingDouble(Exhibition::getPrice);
                    exhList.sort(comparatorP);
                    break;
                case "sortByHall":
                    if (!sortByHall(Long.parseLong(req.getParameter("hall_id")))) {
                        req.getSession().setAttribute(ERROR_MESSAGE, "no exhibition in this hall");
                    }
                    break;
                default:
            }

            logger.info("GetExhibitions Command successfully");

        } catch (DaoException e) {
            logger.info("GetExhibitions Command failed");
            req.getSession().setAttribute(ERROR_MESSAGE, "problem with showing exhibitions");
        }

        //making page to view
        int currentPage = 1;
        if (req.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(req.getParameter(CURRENT_PAGE));
        }
        int noOfRecords = exhList.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        int lastIndex = Math.min(currentPage * RECORDS_PER_PAGE, noOfRecords);
        List<Exhibition> sublist = exhList.subList((currentPage - 1) * RECORDS_PER_PAGE, lastIndex);

        session.setAttribute("exhList", sublist);
        session.setAttribute("noOfPages", noOfPages);
        session.setAttribute("currentPage", currentPage);

        String redirect;
        if (session.getAttribute(ERROR_MESSAGE) != null) {
            redirect = HOME_PAGE;
        } else {
            redirect = EXHIBITION_PAGE;
        }

        try {
            resp.sendRedirect(redirect);
        } catch (IOException e) {
            logger.info("GetExhibitions redirect failed");
        }
    }

    private boolean sortByDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            Comparator<Exhibition> comparator
                    = Comparator.comparing(Exhibition::getStartDate);
            exhList.sort(comparator);
        } else {
            Date date = Date.valueOf(dateString);
            exhList = exhList.stream().filter(e -> (e.getStartDate().before(date) || e.getStartDate().equals(date))
                    && (e.getEndDate().after(date) || e.getEndDate().equals(date))).collect(Collectors.toList());
        }
        return exhList != null && !exhList.isEmpty();
    }

    private boolean sortByHall(long hallId) {

        ExhibitionService service = new ExhibitionDao();
        List<Exhibition> hallExhibition = new CopyOnWriteArrayList<>();
        for (Exhibition exh : exhList) {
            List<Hall> halls = service.getHalls(exh.getId());
            for (Hall hall : halls) {
                if (hall.getId() == hallId) {
                    hallExhibition.add(exh);
                    break;
                }
            }
        }

        exhList = hallExhibition;
        return !hallExhibition.isEmpty();
    }
}
