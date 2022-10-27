package com.exhibit.controller.commands.impl.common;

import com.exhibit.controller.commands.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.exhibit.util.constants.UtilConstants.HOME_PAGE;
import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class LogOut implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp){
        HttpSession session = req.getSession();
        session.invalidate();
        try {
            resp.sendRedirect(req.getHeader("Referer"));
        } catch (IOException e) {
            logger.info(e);
        }
    }
}
