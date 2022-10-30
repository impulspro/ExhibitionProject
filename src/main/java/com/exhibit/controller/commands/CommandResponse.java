package com.exhibit.controller.commands;

import com.exhibit.util.constants.DispatchCommand;
import com.exhibit.util.constants.DispatchType;

public class CommandResponse {

    private DispatchType dispatchType;
    private DispatchCommand dispatchCommand;

    private String page;

    public CommandResponse(final DispatchType dispatchType, final String page) {
        this.dispatchType = dispatchType;
        this.page = page;

    }

    public CommandResponse(final DispatchType dispatchType, final DispatchCommand dispatchCommand) {
        this.dispatchType = dispatchType;
        this.dispatchCommand = dispatchCommand;

    }

    public CommandResponse(final DispatchType dispatchType, final DispatchCommand dispatchCommand, final String showPage) {
        this.dispatchCommand = dispatchCommand;
        this.dispatchType = dispatchType;
        this.page = showPage;

    }

    public DispatchType getType() {
        return dispatchType;
    }

    public DispatchCommand getDispatchCommand() {
        return dispatchCommand;
    }

    public String getPage() {
        return page;
    }

}
