package com.exhibit.dao.mappers.impl;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.model.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketMapper implements Mapper<Ticket> {

    public Ticket extractFromResultSet(ResultSet rs) throws SQLException {
        return Ticket.newBuilder()
                .setId(rs.getLong("id"))
                .setUserId(rs.getLong("user_id"))
                .setExhibitionId(rs.getLong("exhibition_id"))
                .build();
    }
}


