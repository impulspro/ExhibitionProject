package com.exhibit.controller;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.exhibit.util.constants.UtilConstants.HOME_PAGE;
import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;


@WebServlet(name = "indexServlet", value = {"/index-servlet"})
public class FrontController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void init() {
        logger.info("Initializing servlet");
    }


    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) {
        commandManager(req, resp);
    }

    private void commandManager(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession session = req.getSession();

        if (session.isNew()) {
            session.setAttribute("user", null);
        }

        String commandName = req.getParameter("command");
        logger.info(commandName);
        Command command = CommandContainer.getCommand(commandName);
        command.execute(req, resp);
        if (commandName == null || commandName.isEmpty()) {
            try {
                req.getRequestDispatcher(HOME_PAGE).forward(req, resp);
            } catch (Exception e) {
                logger.info("No define command");
            }
        }
    }
}