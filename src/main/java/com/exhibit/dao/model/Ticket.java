package com.exhibit.dao.model;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Serializable {
    private long id;
    private long userId;

    private long exhibitionId;

    public static Ticket.Builder newBuilder() {
        return new Ticket().new Builder();
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getExhibitionId() {
        return exhibitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && userId == ticket.userId && exhibitionId == ticket.exhibitionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, exhibitionId);
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
