package com.exhibit.services;

import com.exhibit.dao.model.Exhibition;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface ExhibitionService {
    void add(final Exhibition exhibition);

    Optional<Exhibition> findByTheme(final String theme);

    List<Exhibition> findAll();
    List<Exhibition> findByDatePerPage(final Date date, final int pageNum);
    List<Exhibition> findAllSortByDatePerPage(final int pageNum);

    int amountOfTickets(final long exhibitionId);

    Optional<Exhibition> findById(final long exhibitionId);

    void cancel(final long exhibitionId);

    void delete(final long exhibitionId);
}
