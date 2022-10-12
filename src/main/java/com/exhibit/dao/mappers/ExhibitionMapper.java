package com.exhibit.dao.mappers;

import com.exhibit.model.Exhibition;

import java.sql.ResultSet;
import java.sql.SQLException;
public class ExhibitionMapper implements Mapper<Exhibition> {

    public Exhibition extractFromResultSet(ResultSet rs) throws SQLException {
        return Exhibition.newBuilder()
                .setId(rs.getLong("id"))
                .setTheme(rs.getString("theme"))
                .setDetails(rs.getString("details"))
                .setStartDate(rs.getDate("start_date"))
                .setEndDate(rs.getDate("end_date"))
                .setStartTime(rs.getTime("start_time"))
                .setEndTime(rs.getTime("end_time"))
                .setPrice(rs.getDouble("price"))
                .build();
    }
}
