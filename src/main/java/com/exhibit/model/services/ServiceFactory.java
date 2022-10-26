package com.exhibit.model.services;

import com.exhibit.dao.impl.ExhibitionDao;
import com.exhibit.dao.impl.HallDao;
import com.exhibit.dao.impl.UserDao;

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


