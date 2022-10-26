package com.exhibit.controller.commands.impl;

import com.exhibit.controller.commands.Command;
import com.exhibit.model.User;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import com.exhibit.util.PasswordHashing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.exhibit.util.constants.UtilConstants.*;

public class Registration implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        password = PasswordHashing.toMD5(password);
        HttpSession session = req.getSession();
        String redirectPage = "index.jsp";
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            User user = new User(login, password);
            if (userService.findByLogin(login).isPresent()) {
                String info = "Registration command execute failed for login because of existing user in base = " + login;
                logger.info(info);
                redirectPage = "view/page/registration.jsp";
                req.getSession().setAttribute(ERROR_MESSAGE, "user already exist");
            } else {
                userService.add(user);
                session.setAttribute("user", user);
                session.setAttribute(USER_MESSAGE, "successful registration");
                String info = "Registration command execute successful for login = " + login;
                logger.info(info);
            }

        } catch (Exception e) {
            String info = "Registration command execute failed for login = " + login;
            logger.info(info);
            req.getSession().setAttribute(ERROR_MESSAGE, "registration error");
        }
        try {
            resp.sendRedirect(redirectPage);
        } catch (IOException e) {
            logger.info("Registration failed redirect");
        }
    }
}
