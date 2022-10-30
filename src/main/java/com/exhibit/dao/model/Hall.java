package com.exhibit.dao.model;

import com.exhibit.services.ExhibitionService;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Hall implements Serializable {
    private long id;
    private String name;
    private String details;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

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
    public List<Date> getOccupiedDates(){
        HallService hallService = ServiceFactory.getInstance().getHallService();
        List<Date> dateList = hallService.getOccupiedDates(id);
        Collections.sort(dateList);
        return dateList;
    }
    public List<Exhibition> getExhibitions(){
        HallService hallService = ServiceFactory.getInstance().getHallService();
        return hallService.findAllExhibitionsByHall(id);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return id == hall.id && Objects.equals(name, hall.name) && Objects.equals(details, hall.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, details);
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
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
