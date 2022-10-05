package com.exhibit.model;

public class Ticket{
    private long user_id;
    private long exhibition_id;

    private Ticket() {
    }
    public static Ticket.Builder newBuilder() {
        return new Ticket().new Builder();
    }

    public class Builder {
        private Builder() {
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
