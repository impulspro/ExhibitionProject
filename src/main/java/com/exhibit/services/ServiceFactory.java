package com.exhibit.services;

import com.exhibit.dao.ExhibitionDao;
import com.exhibit.dao.HallDao;
import com.exhibit.dao.UserDao;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public ExhibitionService getExhibitionService() {
        return new ExhibitionDao();
    }

    public HallService getHallService() {
        return new HallDao();
    }

    public UserService getUserService() {
        return new UserDao();
    }

}


