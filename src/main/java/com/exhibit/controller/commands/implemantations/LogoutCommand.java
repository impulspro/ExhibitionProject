package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class LogoutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Logout command");
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect("index.jsp");
    }
}
