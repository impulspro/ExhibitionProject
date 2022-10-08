package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.model.User;
import com.exhibit.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class RegistrationCommand implements Command{
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            UserService userService = new UserService();
            User user = new User(login, password);
            userService.add(user);
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("user_message", "successful registration");
            logger.info("Registration command execute successful for login = " + login);
        } catch (Exception e) {
            logger.info("Registration command execute failed for login = " + login );
            req.getSession().setAttribute("error_message", "registration error");
            resp.sendRedirect("registration.jsp");
        }
        resp.sendRedirect("index.jsp");
    }
}
