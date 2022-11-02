package com.exhibit.controller.commands.impl.admin;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.ConnectionManager;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Hall;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;
import com.exhibit.dao.constants.DispatchCommand;
import com.exhibit.dao.constants.DispatchType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

import static com.exhibit.dao.constants.UtilConstants.*;

public class AddExhibition implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp, final ConnectionManager manager) {
        ExhibitionService exhibitionService = ServiceFactory.getInstance().getExhibitionService(manager);
        HallService hallService = ServiceFactory.getInstance().getHallService(manager);

        String theme = req.getParameter("theme");
        String details = req.getParameter("details");
        Date startDate = Date.valueOf(req.getParameter("startDate"));
        Date endDate = Date.valueOf(req.getParameter("endDate"));
        Time startTime = Time.valueOf(req.getParameter("startTime") + ":00");
        Time endTime = Time.valueOf(req.getParameter("endTime") + ":00");
        double price = Double.parseDouble(req.getParameter("price"));
        String[] hallsId = req.getParameterValues("hallsId");

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
            for (String hallId: hallsId) {
                if (hallService.isOccupiedOnDate(Long.parseLong(hallId), startDate, endDate)) {
                    Optional<Hall> hall = hallService.findById(Long.parseLong(hallId));
                    hall.ifPresent(value -> req.getSession().setAttribute(ERROR_MESSAGE, value.getName() + "hall is occupied on this dates"));
                    return new CommandResponse(DispatchType.REDIRECT, DispatchCommand.GO, ADMIN_JSP);
                }
            }

            exhibitionService.add(exhibition);
            hallService.setHallByExhibitionId(exhibition.getId(), hallsId);
            req.getSession().setAttribute(USER_MESSAGE, "you successfully add exhibition");
        } catch (Exception e) {
            logger.error(e);
            req.getSession().setAttribute(ERROR_MESSAGE, "something gone wrong");
        }
        return new CommandResponse(DispatchType.REDIRECT, DispatchCommand.GO, ADMIN_JSP);
    }
}
