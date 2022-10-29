package com.exhibit.controller;

import com.exhibit.controller.commands.CRType;
import com.exhibit.controller.commands.CommandR;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.controller.commands.CommandContainerR;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
        if (commandName == null || commandName.isEmpty()){
            commandName = "getExhibitionsCommand";
        }
        logger.info(commandName);


//        Command command = CommandContainer.getCommand(commandName);
        CommandR commandR = CommandContainerR.getCommand(commandName);
        System.out.println(commandR);
        CommandResponse cr = commandR.execute(req,resp);
        System.out.println(cr);
        //

        System.out.println(cr.getShowPage());
        try {
            session.setAttribute("showPage", "page/" + cr.getShowPage());
            System.out.println(req.getSession().getAttribute("showPage"));
            if (cr.getType().equals(CRType.FORWARD)) {
                req.getRequestDispatcher("view/index.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("view/index.jsp");
            }
        } catch (ServletException | IOException e) {
            logger.error(e);
        }

/*
        if (commandName == null || commandName.isEmpty()) {
            try {
                req.getRequestDispatcher(HOME_PAGE).forward(req, resp);
            } catch (Exception e) {
                logger.info("No define command");
            }
        }

 */
    }
}