package com.exhibit.controller.commands.implR.common;

import com.exhibit.controller.commands.CRType;
import com.exhibit.controller.commands.CommandR;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.model.Hall;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.exhibit.util.constants.UtilConstants.ERROR_MESSAGE;
import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class GetHallsR implements CommandR {

    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            HallService service = ServiceFactory.getInstance().getHallService();
            List<Hall> hallList = service.findAll();
            req.getSession().setAttribute("hallList", hallList);
        } catch (Exception e) {
            logger.error("GetHalls command failed");
            req.getSession().setAttribute(ERROR_MESSAGE, "GetHalls command failed");
        }

        return new CommandResponse(CRType.FORWARD, "showHalls2.jsp");
    }
}
