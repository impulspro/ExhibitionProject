package com.exhibit.controller.commands.impl;

import com.exhibit.controller.commands.Command;
import com.exhibit.model.Hall;
import com.exhibit.model.services.HallService;
import com.exhibit.model.services.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.exhibit.util.constants.UtilConstants.ERROR_MESSAGE;
import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class GetHalls implements Command {

    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {
        String page = "view/page/halls.jsp";
        try {
            HallService service = ServiceFactory.getInstance().getHallService();
            List<Hall> hallList = service.findAll();
            req.getSession().setAttribute("hallList", hallList);
            logger.info("GetHalls command successful");
        } catch (Exception e) {
            logger.info("GetHalls command failed");
            page = "index.jsp";
            req.getSession().setAttribute(ERROR_MESSAGE, "GetHalls command failed");
        }
        try {
            resp.sendRedirect(page);
        } catch (IOException e) {
            logger.info("GetHalls redirect failed");
        }
    }
}
