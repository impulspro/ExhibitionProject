package com.exhibit.dao.mappers.impl;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.model.Hall;

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
