package com.exhibit.controller.commands.impl;

import com.exhibit.controller.commands.Command;
import com.exhibit.dao.exeptions.DaoException;
import com.exhibit.model.Exhibition;
import com.exhibit.model.services.ExhibitionService;
import com.exhibit.model.services.HallService;
import com.exhibit.model.services.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import static com.exhibit.util.constants.UtilConstants.*;

public class AddExhibition implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {
        String page = "view/page/adminPanel.jsp";
        String theme = req.getParameter("theme");
        String details = req.getParameter("details");
        Date startDate = Date.valueOf(req.getParameter("startDate"));
        Date endDate = Date.valueOf(req.getParameter("endDate"));
        Time startTime = Time.valueOf(req.getParameter("startTime") + ":00");
        Time endTime = Time.valueOf(req.getParameter("endTime") + ":00");
        double price = Double.parseDouble(req.getParameter("price"));
        String[] hallsId = req.getParameterValues("hallsId");

        ExhibitionService service = ServiceFactory.getInstance().getExhibitionService();
        Exhibition exhibition = Exhibition.newBuilder()
                .setTheme(theme)
                .setDetails(details)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setPrice(price)
                .build();
        try {
            service.add(exhibition);
            HallService hallService = ServiceFactory.getInstance().getHallService();
            hallService.setHallByExhibitionId(exhibition.getId(), hallsId);
            logger.info("AddExhibition command execute successful");
            req.getSession().setAttribute(USER_MESSAGE, "you successfully add exhibition");
        } catch (DaoException e) {
            logger.info("AddExhibition command failed");
            req.getSession().setAttribute(ERROR_MESSAGE, "something gone wrong");
        }

        try {
            resp.sendRedirect(page);
        } catch (IOException e) {
            logger.info("AddExhibition redirect failed");
        }
    }
}
