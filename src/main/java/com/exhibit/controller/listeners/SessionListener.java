package com.exhibit.controller.listeners;

import com.exhibit.dao.HallDao;
import com.exhibit.model.Hall;
import com.exhibit.services.HallService;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HallService service = new HallDao();
        List<Hall> hallList = service.findAll();
        httpSessionEvent.getSession().setAttribute("hallList", hallList);
    }
}
