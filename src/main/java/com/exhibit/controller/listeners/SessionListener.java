package com.exhibit.controller.listeners;

import com.exhibit.dao.model.Hall;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

import static com.exhibit.util.constants.ExhibitionConstants.SORT_BY_DATE;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HallService hallService = ServiceFactory.getInstance().getHallService();
        List<Hall> hallList = hallService.findAll();
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute("hallList", hallList);
        session.setAttribute("sortType", SORT_BY_DATE);
    }
}

