package com.exhibit.util.constants;

public class UserConstants {
    private UserConstants() {}
    public static final String SETTINGS_FILE = "app.properties";
    public static final String ADMINISTRATOR = "admin";
    public static final String AUTHORIZED_USER = "user";
    public static final String ADD_USER_SQL = "INSERT INTO user VALUES (DEFAULT, ?, ?, ?, DEFAULT)";


    public static final String FIND_USER_BY_ID =
            "SELECT * FROM user WHERE id = ?";
    public static final String FIND_ALL_USERS_SQL =
            "SELECT * FROM user";
    public static final String UPDATE_USER =
            "UPDATE user SET email = ? , login = ?, password = ?, role_id = ? WHERE id = ?";
    public static final String DELETE_USER_BY_ID =
            "DELETE FROM user  WHERE id = ?";
    public static final String FIND_USER_BY_LOGIN_AND_PASSWORD =
            "SELECT * FROM user WHERE login = ? AND password = ?";
    public static final String FIND_USER_BY_LOGIN =
            "SELECT * FROM user WHERE login = ?";
    public static final String SET_ORDERS =
            "INSERT INTO orders VALUES(?, ?)";
    public static final String GET_ID =
            "SELECT id FROM user WHERE login = ?";

    public static final String FIELD_ID = "id";
}

