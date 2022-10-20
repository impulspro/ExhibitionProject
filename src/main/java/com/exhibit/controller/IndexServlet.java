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
import java.io.IOException;

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;


@WebServlet(name = "indexServlet", value = {"/index-servlet"})
public class IndexServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    public void init() throws ServletException {
        super.init();
        logger.info("Initializing servlet");

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        commandManager(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        commandManager(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        commandManager(req, resp);
    }

    public void destroy() {
        super.destroy();
        logger.info("Destroying servlet");
    }

    private void commandManager(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String commandName = req.getParameter("command");
            logger.info(commandName + " command");
            Command command = CommandContainer.getCommand(commandName);
            command.execute(req, resp);
        } catch (Exception e){
            req.getSession().setAttribute("error_message", e);
            resp.sendRedirect("index.jsp");
        }
    }
}