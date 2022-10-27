package com.exhibit;

import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Hall;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ExhibitionService exhibitionService = ServiceFactory.getInstance().getExhibitionService();
        HallService hallService = ServiceFactory.getInstance().getHallService();
        List<Exhibition> list = exhibitionService.findAll();
        System.out.println(list.get(1));
        List<Hall> halls = hallService.getHallByExhibitionId(1);
        halls.stream().forEach(s-> System.out.println(s));
    }
}