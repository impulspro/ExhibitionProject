package com.exhibit.services;

import com.exhibit.dao.ConnectionManager;
import com.exhibit.dao.impl.ExhibitionDao;
import com.exhibit.dao.impl.HallDao;
import com.exhibit.dao.impl.UserDao;

public class ServiceFactory{
    private static final ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public ExhibitionService getExhibitionService(ConnectionManager manager) {
        return new ExhibitionDao(manager);
    }

    public HallService getHallService(ConnectionManager manager) {
        return new HallDao(manager);
    }

    public UserService getUserService(ConnectionManager manager) {
        return new UserDao(manager);
    }

}


