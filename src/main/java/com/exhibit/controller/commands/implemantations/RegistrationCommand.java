package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.util.PasswordHashing;
import com.exhibit.model.User;
import com.exhibit.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.exhibit.util.UtilConstants.INFO_LOGGER;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        password = PasswordHashing.toMD5(password);
        System.out.println(login + password);
        HttpSession session = req.getSession();
        String redirectPage = "index.jsp";
        try {
            UserService userService = new UserService();
            User user = new User(login, password);
            if (userService.findByLogin(login).isPresent()) {
                logger.info("Registration command execute failed for login because of existing user in base = " + login);
                redirectPage = "view/page/registration.jsp";
                req.getSession().setAttribute("error_message", "user already exist");
            } else {
                userService.add(user);
                session.setAttribute("user", user);
                session.setAttribute("user_message", "successful registration");
                logger.info("Registration command execute successful for login = " + login);
            }

        } catch (Exception e) {
            logger.info("Registration command execute failed for login = " + login);
            req.getSession().setAttribute("error_message", "registration error");
        }
        resp.sendRedirect(redirectPage);
    }
}
