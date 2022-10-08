package com.exhibit.dao;

import com.exhibit.exeptions.DBException;
import org.apache.commons.dbcp.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionPool {
    private ConnectionPool() {
    }

    private static BasicDataSource dsBasic = new BasicDataSource();
    static {
        dsBasic.setUrl("jdbc:mysql://localhost:3306/exhibition_db?serverTimezone=Europe/Minsk");
        dsBasic.setUsername("root");
        dsBasic.setPassword("root");
    }

    public static Connection getConnection() throws DBException {
        try {
            return dsBasic.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
     /*   Context ctx;
        Connection c;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DBResourceName");
            c = ds.getConnection();
        } catch (NamingException | SQLException e) {
             throw new DBException("Cannot get connection", e);
        }
        return c;

      */
    }


}