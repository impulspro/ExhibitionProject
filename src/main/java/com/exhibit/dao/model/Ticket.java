package com.exhibit.dao.model;

import com.exhibit.services.ExhibitionService;
import com.exhibit.services.ServiceFactory;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;

public class Ticket implements Serializable {
    private long id;
    private long userId;
    private long exhibitionId;

    private Ticket() {
    }

    public static Ticket.Builder newBuilder() {
        return new Ticket().new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(long exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public Exhibition getExhibition() {
        ExhibitionService service = ServiceFactory.getInstance().getExhibitionService();
        Optional<Exhibition> exhibition = service.findById(exhibitionId);
        return exhibition.orElse(null);
    }

    public boolean isCanBeReturn(){
        Exhibition exhibition = getExhibition();

        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
        Date endDate = exhibition.getEndDate();

        if (endDate.compareTo(date) >= 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "user_id=" + userId +
                ", exhibition_id=" + exhibitionId +
                '}';
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(long id) {
            Ticket.this.id = id;
            return this;
        }

        public Builder setUserId(long userId) {
            Ticket.this.userId = userId;
            return this;
        }

        public Builder setExhibitionId(long exhibitionId) {
            Ticket.this.exhibitionId = exhibitionId;
            return this;
        }

        public Ticket build() {
            return Ticket.this;
        }
    }
}
