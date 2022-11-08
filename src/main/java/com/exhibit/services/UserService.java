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

    void delete(final long userId);

    /**
     *
     * @param login login of user
     * @param exhibitionId id of exhibition
     * @return true if user has ticketed on exhibition
     */
    boolean isTicketPresent(final String login, final long exhibitionId);

    /**
     *
     * @param userId id of user
     * @return list of all user tickets
     */
    List<Ticket> getUserTickets(final long userId);

    /**
     * method that make ticket record in db and reduces user account by the ticket price
     *
     * @param user user object buying ticket
     * @param exhibitionId id of exhibition which user want to view
     * @return "ok" if operation successful or another answer of unsuccessful cause
     */
    String buyTicket(final User user, final long exhibitionId);
}
