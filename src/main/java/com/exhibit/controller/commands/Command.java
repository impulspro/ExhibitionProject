package com.exhibit.controller.commands;

import com.exhibit.dao.connection.ConnectionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    CommandResponse execute(final HttpServletRequest req, final  HttpServletResponse resp, final ConnectionManager manager);
}
