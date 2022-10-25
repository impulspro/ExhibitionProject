package com.exhibit.controller.commands.impl;

import com.exhibit.controller.commands.Command;
import com.exhibit.dao.UserDao;
import com.exhibit.model.User;
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
        String password = req.getParameter("password");
        password = PasswordHashing.toMD5(password);
        String page = "index.jsp";

        try {
            UserService userService = new UserDao();
            Optional<User> user;
            user = userService.findByLogin(login);
            if (user.isPresent()) {
                if (user.get().getPassword().equals(password)) {
                    req.getSession().setAttribute("user", user.get());
                    String info = "LogIn command execute successful for login = " + login;
                    logger.info(info);
                }
            } else {
                req.getSession().setAttribute(ERROR_MESSAGE, "login/password dont match with db");
            }
            page = req.getHeader("Referer");
        } catch (Exception e) {
            String info = "LogIn command execute failed for login = " + login;
            logger.info(info);
            req.getSession().setAttribute(ERROR_MESSAGE, "problems in LogInCommand");
        }
        try {
            resp.sendRedirect(page);
        } catch (IOException e) {
            logger.info("problems with Redirect");
        }
