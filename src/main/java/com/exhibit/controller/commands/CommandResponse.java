package com.exhibit.controller.commands;

import com.exhibit.dao.constants.DispatchCommand;
import com.exhibit.dao.constants.DispatchType;

public class CommandResponse {

    private DispatchType dispatchType;
    private DispatchCommand dispatchCommand;

    public DispatchType getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(DispatchType dispatchType) {
        this.dispatchType = dispatchType;
    }

    public void setDispatchCommand(DispatchCommand dispatchCommand) {
        this.dispatchCommand = dispatchCommand;
    }

    public void setPage(String page) {
        this.page = page;
    }

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
