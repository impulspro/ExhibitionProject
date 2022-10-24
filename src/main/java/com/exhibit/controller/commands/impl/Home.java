package com.exhibit.controller.commands.impl;

import com.exhibit.controller.commands.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.exhibit.util.UtilConstants.INFO_LOGGER;

public class Home implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            resp.sendRedirect("index.jsp");
        } catch (IOException e) {
            logger.info("Home redirect failed");
        }
    }
}
