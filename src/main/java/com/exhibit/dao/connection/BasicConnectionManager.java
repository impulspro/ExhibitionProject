package com.exhibit.dao.connection;

import com.exhibit.dao.exeptions.DaoException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static com.exhibit.dao.constants.UtilConstants.*;


public class BasicConnectionManager implements ConnectionManager {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    private static final BasicDataSource ds = new BasicDataSource();

    private static ConnectionManager manager;

    static {
        try (InputStream in = BasicConnectionManager.class.getClassLoader().
                getResourceAsStream("db/basicDB.properties")) {
            Properties prop = new Properties();
            prop.load(in);
            ds.setDriverClassName(prop.getProperty("db.driver.classname"));
            ds.setUrl(prop.getProperty("db.url"));
            ds.setUsername(prop.getProperty("db.username"));
            ds.setPassword(prop.getProperty("db.password"));
        } catch (Exception e) {
            logger.error(PROBLEMS_WITH_CONNECTION_POOL);
            throw new DaoException(PROBLEMS_WITH_CONNECTION_POOL, e);
        }
    }

    private BasicConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (manager == null)
            manager = new BasicConnectionManager();
        return manager;

    }
    public Connection getConnection() {
        try {
            Connection conn = ds.getConnection();
            //snapshot isolation level
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            logger.error(PROBLEMS_WITH_GET_CONNECTION);
            throw new DaoException(PROBLEMS_WITH_GET_CONNECTION);
        }
    }

    public void closeResources(AutoCloseable... resources) {
        for (AutoCloseable res : resources) {
            if (res != null) {
                try {
                    res.close();
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }

    public void rollbackConnection(Connection conn, SQLException e) {
        logger.error(e);
        try {
            conn.rollback();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }
}

   