package com.exhibit.controller;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandContainer;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "indexServlet", value = {"/index-servlet"})
public class IndexServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        commandManager(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        commandManager(req, resp);
    }

    public void destroy() {
    }

    private void commandManager(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        HttpSession httpSession = req.getSession();
        if (httpSession.isNew()) {
            httpSession.setAttribute("user", null);
        }

        String page = "index.jsp";
        String commandName = req.getParameter("command");
        if (commandName == null) {
            req.getRequestDispatcher(page).forward(req, resp);
        } else {
            Command command = CommandContainer.getCommand(commandName);
            page = CommandContainer.doCommand(command, req);
            if (page.contains("redirect")) {
                resp.sendRedirect(page.replace("redirect:", ""));
            } else {
                req.getRequestDispatcher(page).forward(req, resp);
            }
        }
    }
}