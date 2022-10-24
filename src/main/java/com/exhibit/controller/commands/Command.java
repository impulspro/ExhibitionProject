package com.exhibit.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    void execute(final HttpServletRequest req, final HttpServletResponse resp);
}
