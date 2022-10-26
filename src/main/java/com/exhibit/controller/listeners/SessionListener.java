package com.exhibit.controller.listeners;

import com.exhibit.model.Hall;
import com.exhibit.model.services.HallService;
import com.exhibit.model.services.ServiceFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HallService hallService = ServiceFactory.getInstance().getHallService();
        List<Hall> hallList = hallService.findAll();
        httpSessionEvent.getSession().setAttribute("hallList", hallList);
    }
}
