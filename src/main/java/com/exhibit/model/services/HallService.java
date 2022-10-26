package com.exhibit.model.services;

import com.exhibit.model.Hall;

import java.util.List;

public interface HallService {
    List<Hall> findAll();
    List<Hall> getHallByExhibitionId(final long exhibitionId);
    void setHallByExhibitionId(final long exhibitionId, final String[] hallsId);
}
