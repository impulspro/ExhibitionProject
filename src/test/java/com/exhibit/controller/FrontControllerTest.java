package com.exhibit.controller;

import com.exhibit.dao.connection.BasicConnectionManager;
import com.exhibit.dao.connection.ConnectionManager;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.exhibit.dao.constants.ExhibitionConstants.SORT_BY_DATE;
import static com.exhibit.dao.constants.UtilConstants.*;
import static org.mockito.Mockito.*;

/**
 * testing with using Mockito for controller package
 */
class FrontControllerTest {
    static ExhibitionService exhibitionService;
    static UserService userService;
    static HallService hallService;

    @BeforeAll
    static void globalSetUp() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ConnectionManager manager = BasicConnectionManager.getInstance();
        exhibitionService = serviceFactory.getExhibitionService(manager);
        userService = serviceFactory.getUserService(manager);
        hallService = serviceFactory.getHallService(manager);
    }

    @Test
    void ServletMockTest() throws ServletException, IOException {
        final FrontController servlet = new FrontController();
        final HttpServletRequest req = mock(HttpServletRequest.class);
        final HttpServletResponse resp = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final HttpSession session = mock(HttpSession.class);
        when(session.getAttribute(SORT_TYPE)).thenReturn(SORT_BY_DATE);
        when(session.getAttribute(SORT_PARAM)).thenReturn("");
        when(req.getParameter("command")).thenReturn("getExhibitionsCommand");
        when(req.getSession()).thenReturn(session);
        when(req.getRequestDispatcher(HOME_PAGE)).thenReturn(dispatcher);

        servlet.service(req, resp);
        verify(dispatcher).forward(req, resp);
        verify(req, atLeast(1)).getParameter("command");
        verify(session, atLeast(1)).getAttribute(SORT_TYPE);
        verify(session, atLeast(1)).getAttribute(SORT_PARAM);

        List<Exhibition> exhibitionsList = exhibitionService.findSortByWhereIs(SORT_BY_DATE, "", 1);
        verify(session).setAttribute(CURRENT_PAGE, 1);
        verify(session).setAttribute(AMOUNT_OF_PAGES, 2);
        verify(session).setAttribute(EXHIBITIONS_LIST, exhibitionsList);
    }
}