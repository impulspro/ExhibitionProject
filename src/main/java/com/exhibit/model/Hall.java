package com.exhibit.model;

import java.util.Objects;

public class Hall {
    private long id;
    private String name;
    private String details;

    public Hall(long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Hall() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Hall hall = (Hall) o;
        return Objects.equals(name, hall.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Hall{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public static Hall.Builder newBuilder() {
        return new Hall().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(long id) {
            Hall.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            Hall.this.name = name;
            return this;
        }

        public Builder setDetails(String details) {
            Hall.this.details = details;
            return this;
        }

        public Hall build() {
            return Hall.this;
        }
    }
}
