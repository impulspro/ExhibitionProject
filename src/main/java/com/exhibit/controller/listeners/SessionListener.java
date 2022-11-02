package com.exhibit.controller.listeners;

import com.exhibit.dao.BasicConnectionManager;
import com.exhibit.dao.ConnectionManager;
import com.exhibit.services.ServiceFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import static com.exhibit.dao.constants.ExhibitionConstants.SORT_BY_DATE;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        ConnectionManager manager = BasicConnectionManager.getInstance();
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute("hallService", ServiceFactory.getInstance().getHallService(manager));
        session.setAttribute("userService", ServiceFactory.getInstance().getUserService(manager));
        session.setAttribute("exhibitionService", ServiceFactory.getInstance().getExhibitionService(manager));
        session.setAttribute("sortType", SORT_BY_DATE);
    }
}

