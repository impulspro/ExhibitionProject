package com.exhibit.services;

import com.exhibit.dao.DaoException;
import com.exhibit.dao.ExhibitionDao;
import com.exhibit.dao.UserDao;
import com.exhibit.exeptions.DBException;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Hall;

import java.util.List;


public class ExhibitionService {

    ExhibitionDao dao = new ExhibitionDao();
    public void addExhibition(Exhibition exhibition) throws DaoException {
        dao.add(exhibition);
    }

    public Exhibition findByTheme(String theme) throws DaoException {
        return  dao.findByTheme(theme);
    }
    public List<Exhibition> findAll() throws DaoException {
        return  dao.findAll();
    }

    public void setHalls(long exhibition_id, String[] halls_id) throws DaoException, DBException {
        dao.setHalls(exhibition_id, halls_id);
    }

    public List<Hall> getHalls(long id) {
        return dao.getHalls(id);
    }
}
