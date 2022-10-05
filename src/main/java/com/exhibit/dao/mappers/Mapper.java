package com.exhibit.dao.mappers;

import com.exhibit.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<Entity> {
    Entity extractFromResultSet(ResultSet rs) throws SQLException;
}
