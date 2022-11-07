package com.exhibit.controller.commands.impl.user;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.connection.ConnectionManager;
import com.exhibit.dao.model.User;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import com.exhibit.dao.constants.DispatchCommand;
import com.exhibit.dao.constants.DispatchType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.exhibit.dao.constants.UtilConstants.*;

public class BuyTicket implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp, final ConnectionManager manager) {
        UserService userService = ServiceFactory.getInstance().getUserService(manager);
        HttpSession session = req.getSession();

        long exhibitionId = Long.parseLong(req.getParameter("exhibitionId"));
        User user = (User) req.getSession().getAttribute("user");

        String answer = userService.buyTicket(user, exhibitionId);

        if (answer != null && answer.equals("ok")) {
            session.setAttribute(USER_MESSAGE, "see you at the exhibition");
        } else {
            logger.info(answer);
            session.setAttribute(ERROR_MESSAGE, answer);
        }

        return new CommandResponse(DispatchType.REDIRECT, DispatchCommand.STAY);
    }
}
