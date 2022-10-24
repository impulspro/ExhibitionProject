package com.exhibit.services;

import com.exhibit.model.Ticket;
import com.exhibit.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByLogin(String login);

    List<User> findAll();

    void add(User user);

    void delete(User user);

    String buyTicket(User user, long exhibitionId);

    List<Ticket> getUserTickets(User user);
}
   