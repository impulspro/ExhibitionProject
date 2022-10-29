package com.exhibit.controller.listeners;

import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Hall;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

import static com.exhibit.util.constants.ExhibitionConstants.SORT_BY_DATE;
import static com.exhibit.util.constants.UtilConstants.*;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HallService hallService = ServiceFactory.getInstance().getHallService();
        List<Hall> hallList = hallService.findAll();
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute("hallList", hallList);


        int currentPage = 1;

        ExhibitionService service = ServiceFactory.getInstance().getExhibitionService();
        int amountOfExhibitions = service.amountOfExhibitions(SORT_BY_DATE, null);
        int amountOfPages = (int) Math.ceil(amountOfExhibitions * 1.0 / RECORDS_PER_PAGE);
        List<Exhibition> exhibitionsList = service.findSortByWhereIs(SORT_BY_DATE, null, currentPage);

        session.setAttribute(EXHIBITIONS_LIST, exhibitionsList);
        session.setAttribute(AMOUNT_OF_PAGES, amountOfPages);
        session.setAttribute(CURRENT_PAGE, currentPage);
        session.setAttribute("command","getExhibitionsCommand");
        session.setAttribute("sortType",SORT_BY_DATE);

       // session.setAttribute("showPage", "page/showHalls2.jsp");
     //   session.setAttribute("showPage", "/WEB-INF/jspf/halls.jspf");
    }
}

