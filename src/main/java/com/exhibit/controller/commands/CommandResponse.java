package com.exhibit.controller.commands;

import com.exhibit.dao.constants.DispatchCommand;
import com.exhibit.dao.constants.DispatchType;

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

    public void setDispatchType(DispatchType dispatchType) {
        this.dispatchType = dispatchType;
    }

    public DispatchType getDispatchType() {
        return dispatchType;
    }

    public DispatchCommand getDispatchCommand() {
        return dispatchCommand;
    }

    public void setDispatchCommand(DispatchCommand dispatchCommand) {
        this.dispatchCommand = dispatchCommand;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

}
