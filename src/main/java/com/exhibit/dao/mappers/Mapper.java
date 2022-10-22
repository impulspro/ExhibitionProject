package com.exhibit.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<Entity> {
    Entity extractFromResultSet(ResultSet rs) throws SQLException;
}
