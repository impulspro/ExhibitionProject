package com.exhibit.dao;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.exeptions.DBException;
import com.exhibit.model.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.util.constants.UserConstants.*;


public class UserDao {
    static Mapper mapper = MapperFactory.getInstance().getUserMapper();

    public static User findByLogin(String login) throws DBException {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_USER_BY_LOGIN)){
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return (User) mapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Cannot find user", e);
        }
        return null;
    }

    public static void add(User user) throws DBException {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(ADD_USER_SQL)) {
            int i = 1;
            prepSt.setString(i++, user.getLogin());
            prepSt.setString(i++, user.getPassword());
            prepSt.setString(i++, AUTHORIZED_USER);
            prepSt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Invalid user input", e);
        }
    }

    public List<User> findAll() {
        List<User> userList = new CopyOnWriteArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_USERS_SQL)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long asdf = rs.getLong(1);
                User user = (User) mapper.extractFromResultSet(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DBException("Cannot find all users", e);
        }
        return userList;
    }
}
