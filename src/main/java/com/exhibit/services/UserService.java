package com.exhibit.services;

import com.exhibit.dao.model.Ticket;
import com.exhibit.dao.model.User;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public interface UserService extends Serializable {
    void add(final User user);
    void returnTicket(final User user, final long exhibitionId);

    Optional<User> findByLogin(final String login);

    List<User> findAll();

    void update(final User user);

    void delete(final User user);

    boolean isTicketPresent(final String login, final long exhibitionId);

    List<Ticket> getUserTickets(final long userId);

    String buyTicket(final User user, final long exhibitionId);
}
