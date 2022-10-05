package com.exhibit.dao.mappers;

import com.exhibit.model.Hall;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HallMapper implements Mapper<Hall> {

    public Hall extractFromResultSet(ResultSet rs) throws SQLException {
        return Hall.newBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setDetails(rs.getString("details"))
                .build();
    }
}
