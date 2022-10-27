package com.exhibit.controller.commands.impl.common;

import com.exhibit.controller.commands.Command;
import com.exhibit.dao.model.User;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import com.exhibit.util.PasswordHashing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.exhibit.util.constants.UtilConstants.ERROR_MESSAGE;
import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class LogIn implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = PasswordHashing.toMD5(req.getParameter("password"));

        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
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
            logger.info(e);
            req.getSession().setAttribute(ERROR_MESSAGE, "problems in LogInCommand");
        }
        try {
            resp.sendRedirect(req.getHeader("Referer"));
        } catch (IOException e) {
            logger.info(e);
        }
    }
}
