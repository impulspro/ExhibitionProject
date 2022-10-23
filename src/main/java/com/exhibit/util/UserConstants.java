package com.exhibit.util;

public class UserConstants {
    public static final String AUTHORIZED_USER = "user";
    public static final double USER_DEFAULT_MONEY = 1000;
    public static final String ADD_USER_SQL = "INSERT INTO user VALUES (DEFAULT, ?, ?, ?, ?)";
    public static final String ADD_USER_TICKET_SQL = "INSERT INTO user_tickets VALUES (DEFAULT, ?, ?)";
    public static final String FIND_REAL_USER_ID_BY_LOGIN_SQL = "SELECT * FROM user WHERE login = ?";
    public static final String FIND_ALL_USERS_SQL =
            "SELECT * FROM user";
    public static final String FIND_USER_TICKETS_SQL = "SELECT * FROM user_tickets WHERE user_id = ?";
    public static final String UPDATE_USER_SQL =
            "UPDATE user SET login = ?, password = ?, role = ?, money = ? WHERE id = ?";
    public static final String FIND_USER_BY_LOGIN =
            "SELECT * FROM user WHERE login = ?";
    public static final String DELETE_USER_BY_ID_SQL =
            "DELETE FROM user WHERE id = ?";
    private UserConstants() {
    }
}

