package com.exhibit.services;

import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Hall;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HallService extends Serializable {
    List<Hall> findAll();
    Optional<Hall> findById(final long hallId);
    List<Exhibition> findAllExhibitionsByHall(final long hallId);

    List<Hall> getHallsByExhibitionId(final long exhibitionId);
    void setHallByExhibitionId(final long exhibitionId, final String[] hallsId);
    List<Date> getOccupiedDates(final long hallId);
    boolean isOccupiedBetweenTwoDates(final long hallId, final Date startDate, final Date endDate);
}
