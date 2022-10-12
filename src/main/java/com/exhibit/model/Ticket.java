package com.exhibit.model;

public class Ticket{
    private long id;
    private long user_id;
    private long exhibition_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getExhibition_id() {
        return exhibition_id;
    }

    public void setExhibition_id(long exhibition_id) {
        this.exhibition_id = exhibition_id;
    }

    private Ticket() {
    }

    public static Ticket.Builder newBuilder() {
        return new Ticket().new Builder();
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "user_id=" + user_id +
                ", exhibition_id=" + exhibition_id +
                '}';
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(long id) {
            Ticket.this.id = id;
            return this;
        }
        public Builder setUserId(long user_id) {
            Ticket.this.user_id = user_id;
            return this;
        }
        public Builder setExhibitionId(long exhibition_id) {
            Ticket.this.exhibition_id = exhibition_id;
            return this;
        }

        public Ticket build() {
            return Ticket.this;
        }
    }
}
