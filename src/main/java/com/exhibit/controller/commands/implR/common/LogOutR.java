package com.exhibit.controller.commands.implR.common;

import com.exhibit.controller.commands.CRType;
import com.exhibit.controller.commands.CommandR;
import com.exhibit.controller.commands.CommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class LogOutR implements CommandR {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.invalidate();
        return new CommandResponse(CRType.FORWARD, req.getHeader("Referer"));
    }
}
