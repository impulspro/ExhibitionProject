package com.exhibit.dao;

import com.exhibit.exeptions.DaoException;
import org.apache.commons.dbcp.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//Connection for test purposes without start web application
public class ConnectionPool {
    private ConnectionPool() {
    }

    private static BasicDataSource dsBasic = new BasicDataSource();
    static {
        dsBasic.setUrl("jdbc:mysql://localhost:3306/exhibition_db?");
        dsBasic.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dsBasic.setUsername("root");
        dsBasic.setPassword("root");
    }

    public static Connection getConnection(){
        try {
            return dsBasic.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


/*
public class ConnectionPool {

    private ConnectionPool() {
    }

    public static Connection getConnection(){
        Context ctx;
        Connection c;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DBResourceName");
            c = ds.getConnection();
        } catch (NamingException | SQLException e) {
             throw new DaoException("Cannot get connection", e);
        }
        return c;
    }
}

 */