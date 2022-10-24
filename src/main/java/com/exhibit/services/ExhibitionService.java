package com.exhibit.services;

import com.exhibit.model.Exhibition;
import com.exhibit.model.Hall;

import java.util.List;
import java.util.Optional;


public interface ExhibitionService {
    void add(Exhibition exhibition);


    Optional<Exhibition> findByTheme(String theme);

    List<Exhibition> findAll();

    void setHalls(long exhibitionId, String[] hallsId);

    int amountOfTickets(long exhibitionId);

    List<Hall> getHalls(long id);

    Optional<Exhibition> findById(long exhibitionId);

    void cancel(long exhibitionId);

    void delete(long exhibitionId);
}
