package com.exhibit.services;

import com.exhibit.dao.DaoException;
import com.exhibit.dao.ExhibitionDao;
import com.exhibit.exeptions.DBException;
import com.exhibit.model.Exhibition;

import java.util.List;


public class ExhibitionService {

    public void addExhibition(Exhibition exhibition) throws DaoException {
        ExhibitionDao.add(exhibition);
    }

    public Exhibition findByTheme(String theme) throws DaoException {
        return  ExhibitionDao.findByTheme(theme);
    }
    public List<Exhibition> findAll() throws DaoException {
        return  ExhibitionDao.findAll();
    }

    public void setHalls(long exhibition_id, String[] halls_id) throws DaoException, DBException {
        ExhibitionDao.setHalls(exhibition_id, halls_id);
    }
}
