package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().setAttribute("user", null);
        return "index.jsp";
    }
}
