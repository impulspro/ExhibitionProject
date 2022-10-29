package com.exhibit.controller.commands;

public class CommandResponse {

    private CRType type;

    private String showPage;

    public CommandResponse(final CRType crType, final String showPage) {
        this.type = crType;
        this.showPage = showPage;

    }

    public CRType getType() {
        return type;
    }

    public String getShowPage() {
        return showPage;
    }

}
