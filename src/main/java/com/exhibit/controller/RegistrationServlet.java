package com.exhibit.controller;

import com.exhibit.dao.DaoException;
import com.exhibit.dao.UserDao;
import com.exhibit.exeptions.DBException;
import com.exhibit.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "registrationServlet", value = "/registration-servlet")
public class RegistrationServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GET request Registration servlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            UserDao.add(new User(login, password));
            request.setAttribute("alertMsg", "data add success");
            RequestDispatcher rd=request.getRequestDispatcher("registration.jsp");
            rd.include(request, response);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("registration.jsp");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
