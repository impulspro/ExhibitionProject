package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.dao.DaoException;
import com.exhibit.dao.UserDao;
import com.exhibit.exeptions.DBException;
import com.exhibit.model.User;
import com.exhibit.services.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Pattern;

public class RegistrationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            UserService userService = new UserService();
            User user = new User(login, password);
            userService.add(user);
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("user_message", "successful registration");
        } catch (Exception e) {
            req.getSession().setAttribute("error_message", "registration error");
            return "index.jsp";
        }
        return "index.jsp";
    }
}
