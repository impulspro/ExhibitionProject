package com.exhibit.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManager extends Serializable {
    Connection getConnection();
    void closeResources(AutoCloseable... resources);
    void rollbackConnection(Connection conn, SQLException e);
}
