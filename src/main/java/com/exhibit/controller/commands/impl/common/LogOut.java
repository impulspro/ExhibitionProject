package com.exhibit.controller.commands.impl.common;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.util.constants.DispatchCommand;
import com.exhibit.util.constants.DispatchType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class LogOut implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        return new CommandResponse(DispatchType.REDIRECT, DispatchCommand.STAY);
    }
}
