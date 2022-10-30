package com.exhibit.controller.commands.impl.common;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.model.Hall;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;
import com.exhibit.util.constants.DispatchCommand;
import com.exhibit.util.constants.DispatchType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.exhibit.util.constants.UtilConstants.*;

public class GetHalls implements Command {

    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            HallService service = ServiceFactory.getInstance().getHallService();
            List<Hall> hallList = service.findAll();
            req.getSession().setAttribute("hallList", hallList);
        } catch (Exception e) {
            logger.error(e);
            req.getSession().setAttribute(ERROR_MESSAGE, "GetHalls command failed");
        }

        return new CommandResponse(DispatchType.FORWARD, DispatchCommand.SHOW, HALLS_JSP);
    }
}
