package com.exhibit.services;

import com.exhibit.dao.ExhibitionDao;
import com.exhibit.dao.HallDao;
import com.exhibit.model.Hall;

import java.util.List;

public class HallService {
    static HallDao dao = new HallDao();
    public List<Hall> findAll(){
        return dao.findAll();
    }

}
