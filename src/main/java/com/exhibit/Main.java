package com.exhibit;

import com.exhibit.dao.BasicConnectionManager;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Hall;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;
import com.exhibit.util.PasswordHashing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        HallService hallService = ServiceFactory.getInstance().getHallService(BasicConnectionManager.getInstance());
        ExhibitionService exhibitionService = ServiceFactory.getInstance().getExhibitionService(BasicConnectionManager.getInstance());
        Optional<Exhibition> exhibition =  exhibitionService.findById(8);
        System.out.println(exhibition);

        List<Hall> hallList = hallService.findAll();
        System.out.println(PasswordHashing.toMD5("111111"));
        System.out.println(PasswordHashing.toMD5("222222"));
        System.out.println(PasswordHashing.toMD5("333333"));



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse("2022-09-01");
            Date date2 = sdf.parse("2022-09-03");
            System.out.println(exhibitionService.inPast(exhibition.get().getId()));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}