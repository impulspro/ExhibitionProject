package com.exhibit.controller.commands.impl.admin;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.ConnectionManager;
import com.exhibit.dao.constants.DispatchCommand;
import com.exhibit.dao.constants.DispatchType;
import com.exhibit.dao.model.User;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.exhibit.dao.constants.UtilConstants.*;

public class SearchUser implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp, final ConnectionManager manager) {
        UserService userService = ServiceFactory.getInstance().getUserService(manager);
        HttpSession session = req.getSession();

        String login = req.getParameter("login");

        try {
            Optional<User> user = userService.findByLogin(login);
            if (user.isPresent()) {
                req.getSession().setAttribute("searchUser", user.get());
            } else {
                session.setAttribute(ERROR_MESSAGE, "no such user in base");
            }
        } catch (Exception e) {
            logger.error(e);
            req.getSession().setAttribute(ERROR_MESSAGE, "SearchUser error");
        }

        return new CommandResponse(DispatchType.FORWARD, DispatchCommand.GO, ADMIN_JSP);
    }
}
