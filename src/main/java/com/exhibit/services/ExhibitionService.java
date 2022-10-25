package com.exhibit.services;

import com.exhibit.model.Exhibition;
import com.exhibit.model.Hall;

import java.util.List;
import java.util.Optional;


public interface ExhibitionService {
    void add(final Exhibition exhibition);


    Optional<Exhibition> findByTheme(final String theme);

    List<Exhibition> findAll();

    void setHalls(final long exhibitionId, final String[] hallsId);

    int amountOfTickets(final long exhibitionId);

    List<Hall> getHalls(final long id);

    Optional<Exhibition> findById(final long exhibitionId);

    void cancel(final long exhibitionId);

    void delete(final long exhibitionId);
}
