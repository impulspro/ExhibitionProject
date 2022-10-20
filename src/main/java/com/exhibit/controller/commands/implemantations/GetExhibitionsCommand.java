package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.exeptions.DaoException;
import com.exhibit.model.Exhibition;
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
import java.util.stream.Collectors;

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class GetExhibitionsCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    List<Exhibition> exhList;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("GetExhibitions Command start ");

        int currentPage = 1;
        int recordsPerPage = 3;
        if(req.getParameter("currentPage") != null)
            currentPage = Integer.parseInt(req.getParameter("currentPage"));


        String redirect = "view/page/exhibitions.jsp";
        HttpSession session = req.getSession();

        String sortType = null;
        if (req.getParameter("sortType") != null){
            sortType = req.getParameter("sortType");
            session.setAttribute("sortType", sortType);
        } else {
            sortType = (String) session.getAttribute("sortType");
        }

        try {
            exhList = new ExhibitionService().findAll();

            if (sortType.equals("sortByDate")){
                if (req.getParameter("exhDate").isEmpty()){
                    Comparator<Exhibition> comparator
                            = (h1, h2) -> h1.getStartDate().compareTo(h2.getStartDate());
                    exhList.sort(comparator);
                } else {
                    Date exhDate = Date.valueOf(req.getParameter("exhDate"));
                    List<Exhibition> sortList = exhList.stream().filter(e -> (e.getStartDate().before(exhDate) || e.getStartDate().equals(exhDate))
                            && (e.getEndDate().after(exhDate) || e.getEndDate().equals(exhDate))).collect(Collectors.toList());
                    exhList = sortList;
                    if (sortList.isEmpty()){
                        redirect = "index.jsp";
                        req.getSession().setAttribute("error_message", "no exhibition on this date");
                    }
                }


            }
            if (sortType.equals("sortByPrice")){
                Comparator<Exhibition> comparator
                        = (h1, h2) -> Double.valueOf(h1.getPrice()).compareTo(h2.getPrice());
                exhList.sort(comparator);
            }

            if (sortType.equals("sortByTheme")){
                Comparator<Exhibition> comparator
                        = (h1, h2) -> h1.getTheme().compareTo(h2.getTheme());
                exhList.sort(comparator);
            }
            logger.info("GetExhibitions Command successfully");

        } catch (DaoException e) {
            logger.info("GetExhibitions Command failed");
            redirect = "index.jsp";
            req.getSession().setAttribute("error_message", "problem with showing exhibitions");
        }

        int noOfRecords = exhList.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        int lastIndex = currentPage * recordsPerPage < noOfRecords ? currentPage * recordsPerPage : noOfRecords;
        System.out.println(lastIndex);
        List<Exhibition> sublist = exhList.subList((currentPage - 1) * recordsPerPage, lastIndex);
        session.setAttribute("exhList", sublist);
        session.setAttribute("noOfPages", noOfPages);
        session.setAttribute("currentPage", currentPage);
        resp.sendRedirect(redirect);
    }
}
