package com.exhibit.services;

import com.exhibit.dao.DaoException;
import com.exhibit.dao.UserDao;
import com.exhibit.exeptions.DBException;
import com.exhibit.model.Hall;
import com.exhibit.model.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    static UserDao dao = new UserDao();
    public void add(String login, String password) throws DBException {
        UserDao.add(new User(login, password));
    }

    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(UserDao.findByLogin(login));
    }

    public List<User> findAll(){
        return dao.findAll();
    }
    public void add(User user) throws DBException {
        UserDao.add(user);
    }

}
