package com.exhibit.dao;

import com.exhibit.exeptions.DBException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionPool {
    private ConnectionPool() {
    }

    public static Connection getConnection() throws DBException {
        Context ctx;
        Connection c;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DBResourceName");
            c = ds.getConnection();
        } catch (NamingException | SQLException e) {
             throw new DBException("Cannot get connection", e);
        }
        return c;
    }
}