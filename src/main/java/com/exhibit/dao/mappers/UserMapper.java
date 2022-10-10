package com.exhibit.dao.mappers;

import com.exhibit.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<User> {

    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return User.newBuilder()
                .setId(rs.getLong("id"))
                .setLogin(rs.getString("login"))
                .setPassword(rs.getString("password"))
                .setRole(rs.getString("role"))
                .build();
    }
}