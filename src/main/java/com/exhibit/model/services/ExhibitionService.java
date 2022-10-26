package com.exhibit.model.services;

import com.exhibit.model.Exhibition;

import java.util.List;
import java.util.Optional;


public interface ExhibitionService {
    void add(final Exhibition exhibition);

    Optional<Exhibition> findByTheme(final String theme);

    List<Exhibition> findAll();

    int amountOfTickets(final long exhibitionId);

    Optional<Exhibition> findById(final long exhibitionId);

    void cancel(final long exhibitionId);

    void delete(final long exhibitionId);
}
