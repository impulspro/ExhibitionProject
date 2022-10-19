package com.exhibit.services;

import com.exhibit.exeptions.DaoException;
import com.exhibit.dao.ExhibitionDao;
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

    public void setHalls(long exhibition_id, String[] halls_id){
        dao.setHalls(exhibition_id, halls_id);
    }

    public int amountOfTickets(long exhibition_id) {
        return dao.amountOfTickets(exhibition_id);
    }
    public List<Hall> getHalls(long id) {
        return dao.getHalls(id);
    }

    public Exhibition findById(long exhibition_id) {
        return  dao.findById(exhibition_id);
    }
    public void cancelExhibition(long exhibition_id){
        dao.cancelExhibition(exhibition_id);
    }

    public void deleteExhibition(long exhibition_id) {
        dao.deleteExhibition(exhibition_id);
    }
}
