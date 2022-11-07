package com.exhibit.controller.commands.impl.common;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.connection.ConnectionManager;
import com.exhibit.dao.constants.DispatchCommand;
import com.exhibit.dao.constants.DispatchType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.exhibit.dao.constants.UtilConstants.HALLS_JSP;

public class GetHalls implements Command {

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp, final ConnectionManager manager) {
        return new CommandResponse(DispatchType.FORWARD, DispatchCommand.SHOW, HALLS_JSP);
    }
}
