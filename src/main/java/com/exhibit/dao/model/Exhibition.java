package com.exhibit.dao.model;

import com.exhibit.services.ExhibitionService;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;

/**
 * Builder pattern realization
 */
public class Exhibition implements Serializable {
    private long id;
    private String theme;
    private String details;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private double price;


    private Exhibition() {
    }

    public static Builder newBuilder() {
        return new Exhibition().new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDetails() {
        return details;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public double getPrice() {
        return price;
    }

    public List<Hall> getHalls() {
        HallService hallService = ServiceFactory.getInstance().getHallService();
        return hallService.getHallByExhibitionId(id);
    }

    public int amountOfTickets() {
        ExhibitionService service = ServiceFactory.getInstance().getExhibitionService();
        return service.amountOfTicketsByExhibition(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exhibition that = (Exhibition) o;
        return Double.compare(that.price, price) == 0 && Objects.equals(theme, that.theme) && Objects.equals(details, that.details) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), theme, details, startDate, endDate, startTime, endTime, price);
    }

    @Override
    public String toString() {
        return "Exhibition{" +
                "theme='" + theme + '\'' +
                ", details =" + details +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", price=" + price +
                ", id=" + id +
                '}';
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(long id) {
            Exhibition.this.id = id;
            return this;
        }

        public Builder setTheme(String theme) {
            Exhibition.this.theme = theme;
            return this;
        }

        public Builder setDetails(String details) {
            Exhibition.this.details = details;
            return this;
        }

        public Builder setStartDate(Date startDate) {
            Exhibition.this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(Date endDate) {
            Exhibition.this.endDate = endDate;
            return this;
        }

        public Builder setStartTime(Time startTime) {
            Exhibition.this.startTime = startTime;
            return this;
        }

        public Builder setEndTime(Time endTime) {
            Exhibition.this.endTime = endTime;
            return this;
        }

        public Builder setPrice(double price) {
            Exhibition.this.price = price;
            return this;
        }

        public Exhibition build() {
            return Exhibition.this;
        }
    }

}