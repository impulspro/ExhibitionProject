package com.exhibit.services;

import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.User;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public interface ExhibitionService extends Serializable {

    Optional<Exhibition> findById(final long exhibitionId);

    Optional<Exhibition> findByTheme(final String theme);

    List<Exhibition> findAll();
    List<User> findAllUsersByExhibitionId(final long exhibitionId);
    int amountOfExhibitions(final String sortType, final String sortParam);
    int amountOfExhibitions();
    // find sorted by sortType per page

    /**
     * method for get exhibitions by sortType per page
     *
     * @param sortType sortByDate, sortByTheme, sortByPrice, sortByHall
     * @param sortParam put null for sortBy without WHERE
     * @param pageNum  current page view to extract from db
     * @return list of exhibitions by given parameters
     */
    List<Exhibition> findSortByWhereIs(final String sortType, final String sortParam, final int pageNum);

    void add(final Exhibition exhibition);
    boolean inPast(final long exhibitionId);
    boolean isTicketCanBeReturnByExhibition(final long exhibitionId);
    void cancel(final long exhibitionId);

    void delete(final long exhibitionId);
    int amountOfTicketsByExhibition(final long exhibitionId);
}
