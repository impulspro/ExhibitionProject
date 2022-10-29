package com.exhibit.controller.commands;

import com.exhibit.controller.commands.CommandResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandR {
    CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp);
}
