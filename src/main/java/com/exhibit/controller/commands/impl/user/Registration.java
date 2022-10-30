package com.exhibit.controller.commands.impl.user;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.model.User;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import com.exhibit.util.PasswordHashing;
import com.exhibit.util.constants.DispatchCommand;
import com.exhibit.util.constants.DispatchType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.exhibit.util.constants.UtilConstants.*;

public class Registration implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        password = PasswordHashing.toMD5(password);
        HttpSession session = req.getSession();
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            User user = new User(login, password);
            if (userService.findByLogin(login).isPresent()) {
                logger.error("Registration command execute failed for login because of existing user in base");
                req.getSession().setAttribute(ERROR_MESSAGE, "user already exist");
                return new CommandResponse(DispatchType.REDIRECT, DispatchCommand.GO, REGISTRATION_JSP);
            } else {
                userService.add(user);
                session.setAttribute("user", user);
                session.setAttribute(USER_MESSAGE, "successful registration");
            }

        } catch (Exception e) {
            logger.info(e);
            req.getSession().setAttribute(ERROR_MESSAGE, "registration error");
        }
        return new CommandResponse(DispatchType.REDIRECT, DispatchCommand.SHOW, (String) session.getAttribute(SHOW_PAGE));
    }
}
