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

        String page = "view/page/exhibitions.jsp";
        HttpSession session = req.getSession();
        String sortType = req.getParameter("sortType");
        System.out.println(sortType);
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
                        page = "index.jsp";
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
            session.setAttribute("exhList", exhList);
            logger.info("GetExhibitions Command successfully");

        } catch (DaoException e) {
            logger.info("GetExhibitions Command failed");
            page = "index.jsp";
            req.getSession().setAttribute("error_message", "problem with showing exhibitions");
        }


        resp.sendRedirect(page);
    }
}
