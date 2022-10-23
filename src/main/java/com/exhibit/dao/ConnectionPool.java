package com.exhibit.dao;

import com.exhibit.exeptions.DaoException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static com.exhibit.util.UtilConstants.INFO_LOGGER;


public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        try (InputStream in = ConnectionPool.class.getClassLoader().
                getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            prop.load(in);

            Class.forName(prop.getProperty("db.driver.classname"));

            ds.setUrl(prop.getProperty("db.url"));
            ds.setUsername(prop.getProperty("db.username"));
            ds.setPassword(prop.getProperty("db.password"));

        } catch (Exception e) {
            logger.info("Problems with connection pool");
            throw new DaoException("Problems with connection pool", e);
        }
    }

    private ConnectionPool() {
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
