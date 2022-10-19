package com.exhibit.services;

import com.exhibit.dao.UserDao;
import com.exhibit.model.Ticket;
import com.exhibit.model.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    static UserDao dao = new UserDao();

    public void add(String login, String password) {
        dao.add(new User(login, password));
    }

    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(dao.findByLogin(login));
    }

    public List<User> findAll() {
        return dao.findAll();
    }

    public void add(User user) {
        dao.add(user);
    }

    public String buyTicket(User user, long exhibition_id) {
        return dao.buyTicket(user, exhibition_id);
    }

    public List<Ticket> getUserTickets(User user) {
        return dao.getUserTickets(user);
    }
}
   