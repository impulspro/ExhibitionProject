package com.exhibit.controller;

import com.exhibit.controller.commands.CommandContainer;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.connection.BasicConnectionManager;
import com.exhibit.dao.connection.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.exhibit.dao.constants.UtilConstants.*;


@WebServlet(name = "indexServlet", value = {"/index-servlet"})
public class FrontController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    private static final ConnectionManager manager = BasicConnectionManager.getInstance();

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

        String commandName = req.getParameter("command");
        if (commandName == null || commandName.isEmpty()) {
            commandName = "getExhibitionsCommand";
        }

        logger.info(commandName);

        CommandResponse cr = CommandContainer.getCommand(commandName).execute(req, resp, BasicConnectionManager.getInstance());

        String dispatchPage = HOME_PAGE;

        switch (cr.getDispatchCommand()) {
            case SHOW:
                session.setAttribute(SHOW_PAGE, cr.getPage());
                break;
            case GO:
                dispatchPage = cr.getPage();
                break;
            case STAY:
                dispatchPage = req.getHeader("Referer");
                break;
            default:
        }

        try {
            switch (cr.getDispatchType()) {
                case FORWARD:
                    req.getRequestDispatcher(dispatchPage).forward(req, resp);
                    break;
                case REDIRECT:
                    resp.sendRedirect(dispatchPage);
                    break;
                default:
            }
        } catch (ServletException | IOException e) {
            logger.error(e);
        }

    }
}