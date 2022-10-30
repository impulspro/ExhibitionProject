package com.exhibit.services;

import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Hall;
import com.exhibit.util.constants.DispatchCommand;
import com.exhibit.util.constants.DispatchType;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HallService {
    List<Hall> findAll();
    Optional<Hall> findById(final long hallId);
    List<Exhibition> findAllExhibitionsByHall(final long hallId);

    List<Hall> getHallByExhibitionId(final long exhibitionId);
    void setHallByExhibitionId(final long exhibitionId, final String[] hallsId);
    List<Date> getOccupiedDates(final long hallId);
    boolean isOccupiedOnDate(final long hallId, final Date date);
    boolean isOccupiedOnDate(final long hallId, final Date startDate, final Date endDate);
}
