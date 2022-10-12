package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.dao.DaoException;
import com.exhibit.model.Exhibition;
import com.exhibit.services.ExhibitionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class GetExhibitionsCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("GetExhibitions Command start ");

        String page = "index.jsp";
        HttpSession session = req.getSession();

        List<Exhibition> exhList = null;
        try {
            exhList = new ExhibitionService().findAll();
            page = "view/page/exhibitions.jsp";
            session.setAttribute("exhList", exhList);
            logger.info("GetExhibitions Command successfully");
        } catch (DaoException e) {
            logger.info("GetExhibitions Command failed");
            req.getSession().setAttribute("error_message", "problem with showing exhibitions");
        }
        resp.sendRedirect(page);
    }
}
