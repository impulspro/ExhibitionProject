package com.exhibit;

import com.exhibit.dao.model.Hall;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HallService hallService = ServiceFactory.getInstance().getHallService();

        List<Hall> hallList = hallService.findAll();

        Hall hall = hallList.get(3);
        List<Date> dataList = hall.getOccupiedDates();

        dataList.forEach(s -> System.out.println(s));
        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse("2022-12-28");
            Date date2 = sdf.parse("2022-12-28");
            System.out.println(hallService.isOccupiedOnDate(hall.getId(), date1, date2));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}