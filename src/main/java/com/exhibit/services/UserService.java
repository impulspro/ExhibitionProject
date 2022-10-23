package com.exhibit.services;

import com.exhibit.dao.UserDao;
import com.exhibit.model.Ticket;
import com.exhibit.model.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    static UserDao dao = new UserDao();

    public Optional<User> findByLogin(String login) {
        return dao.findByLogin(login);
    }

    public List<User> findAll() {
        return dao.findAll();
    }

    public void add(User user) {
        dao.add(user);
    }

    public void delete(User user) {
        dao.delete(user);
    }

    public String buyTicket(User user, long exhibition_id) {
        return dao.buyTicket(user, exhibition_id);
    }

    public List<Ticket> getUserTickets(User user) {
        return dao.getUserTickets(user);
    }
}
   