package com.exhibit.dao.mappers;

import com.exhibit.model.Hall;
import com.exhibit.model.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketMapper {
    public class HallMapper implements Mapper<Ticket> {

        public Ticket extractFromResultSet(ResultSet rs) throws SQLException {
            return Ticket.newBuilder()
                    .setUserId(rs.getLong("user_id"))
                    .setExhibitionId(rs.getLong("exhibition_id"))
                    .build();
        }
    }
}
