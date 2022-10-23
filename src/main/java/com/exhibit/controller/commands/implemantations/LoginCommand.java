package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.util.PasswordHashing;
import com.exhibit.model.User;
import com.exhibit.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.exhibit.util.UtilConstants.INFO_LOGGER;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        password = PasswordHashing.toMD5(password);
        String page = "index.jsp";

        try {
            UserService userService = new UserService();
            Optional<User> user;
            user = userService.findByLogin(login);
            if (user.isPresent()){
                if (user.get().getPassword().equals(password)){
                    req.getSession().setAttribute("user", user.get());
                    logger.info("Login command execute successful for login = " + login);
                }
            } else {
                req.getSession().setAttribute("error_message", "login/password dont match with db");
            }
            page = req.getHeader("Referer");
        } catch (Exception e) {
            logger.info("Login command execute failed for login = " + login);
            req.getSession().setAttribute("error_message", "problems in LoginCommand");
        }
        resp.sendRedirect(page);
    }
}
