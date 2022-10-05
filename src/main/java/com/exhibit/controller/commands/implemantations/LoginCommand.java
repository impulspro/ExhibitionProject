package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.model.User;
import com.exhibit.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if(login == null || login.equals("")) {
            req.getSession().setAttribute("error", "loginLogin");
            return "index.jsp";
        }
        if(password == null || password.equals("")) {
            req.getSession().setAttribute("error", "loginPassword");
            return "index.jsp";
        }

        try {
            Optional<User> user = new UserService().findByLogin(login);
            if (new UserService().findByLogin(login).get().getPassword().equals(password)) {
                req.getSession().setAttribute("user", user.get());
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "login_error");
            return "index.jsp";
        }
        return "index.jsp";
    }
}
