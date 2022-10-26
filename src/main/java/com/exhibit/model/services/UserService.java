package com.exhibit.model.services;

import com.exhibit.model.Ticket;
import com.exhibit.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    void add(final User user);


    Optional<User> findByLogin(final String login);

    List<User> findAll();

    void update(final User user);

    void delete(final User user);

    boolean isTicketPreset(final String login, final long exhibitionId);

    List<Ticket> getUserTickets(final User user);

    String buyTicket(final User user, final long exhibitionId);
}
