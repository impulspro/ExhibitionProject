package com.exhibit.dao.connection;

import com.exhibit.dao.exeptions.DaoException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static com.exhibit.dao.constants.UtilConstants.INFO_LOGGER;


public class TestConnectionManager implements ConnectionManager {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    private static final BasicDataSource ds = new BasicDataSource();
    private static ConnectionManager manager;

    static {
        try (InputStream in = TestConnectionManager.class.getClassLoader().
                getResourceAsStream("db/testDB.properties")) {
            Properties prop = new Properties();
            prop.load(in);

            Class.forName(prop.getProperty("db.driver.classname"));

            ds.setUrl(prop.getProperty("db.url"));
            ds.setUsername(prop.getProperty("db.username"));
            ds.setPassword(prop.getProperty("db.password"));


        } catch (Exception e) {
            logger.error("Problems with connection pool");
            throw new DaoException("Problems with connection pool", e);
        }
    }

    private TestConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (manager == null)
            manager = new TestConnectionManager();
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
            logger.error("Problems with getConnection");
            throw new DaoException("Problems with getConnection", e);
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

