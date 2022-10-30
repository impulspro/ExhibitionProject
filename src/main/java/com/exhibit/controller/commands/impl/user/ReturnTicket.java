package com.exhibit.controller.commands.impl.user;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.model.User;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import com.exhibit.util.constants.DispatchCommand;
import com.exhibit.util.constants.DispatchType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.exhibit.util.constants.UtilConstants.*;

public class ReturnTicket implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    @Override
    public CommandResponse execute(HttpServletRequest req, HttpServletResponse resp) {
        long exhibitionId = Long.parseLong(req.getParameter("exhibitionId"));

        User user = (User) req.getSession().getAttribute("user");
        UserService service = ServiceFactory.getInstance().getUserService();
        HttpSession session = req.getSession();

        try {
            service.returnTicket(user, exhibitionId);
        } catch (Exception e){
            logger.info(e);
            session.setAttribute(ERROR_MESSAGE, "Cannot return ticket");
        }

        return new CommandResponse(DispatchType.REDIRECT, DispatchCommand.GO, USER_JSP);
    }
}
