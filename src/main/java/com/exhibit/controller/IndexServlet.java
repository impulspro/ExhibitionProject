package com.exhibit.controller;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.exhibit.util.UtilConstants.INFO_LOGGER;


@WebServlet(name = "indexServlet", value = {"/index-servlet"})
public class IndexServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("Initializing servlet");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        commandManager(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        commandManager(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        logger.info("Destroying servlet");
    }

    private void commandManager(HttpServletRequest req, HttpServletResponse resp) {
        String commandName = req.getParameter("command");
        logger.info(commandName);
        Command command = CommandContainer.getCommand(commandName);
        command.execute(req, resp);
        if (commandName == null || commandName.isEmpty()) {
            try {
                resp.sendRedirect("index.jsp");
            } catch (Exception e) {
                logger.info("No define command");
            }
        }
    }
}