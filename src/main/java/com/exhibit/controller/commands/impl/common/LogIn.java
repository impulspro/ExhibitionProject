package com.exhibit.controller.commands.impl.common;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.connection.ConnectionManager;
import com.exhibit.dao.constants.DispatchCommand;
import com.exhibit.dao.constants.DispatchType;
import com.exhibit.dao.model.User;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import com.exhibit.util.PasswordHashing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.exhibit.dao.constants.UtilConstants.ERROR_MESSAGE;
import static com.exhibit.dao.constants.UtilConstants.INFO_LOGGER;

public class LogIn implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp, final ConnectionManager manager) {
        UserService userService = ServiceFactory.getInstance().getUserService(manager);

        String login = req.getParameter("login");
        String password = PasswordHashing.toMD5(req.getParameter("password"));

        try {
            Optional<User> user;
            user = userService.findByLogin(login);
            if (user.isPresent()) {
                if (user.get().getPassword().equals(password)) {
                    req.getSession().setAttribute("user", user.get());
                }
            } else {
                req.getSession().setAttribute(ERROR_MESSAGE, "login/password dont match with db");
            }
        } catch (Exception e) {
            logger.error(e);
            req.getSession().setAttribute(ERROR_MESSAGE, "problems in LogInCommand");
        }

        return new CommandResponse(DispatchType.REDIRECT, DispatchCommand.STAY);
    }
}
