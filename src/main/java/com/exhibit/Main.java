package com.exhibit;

import com.exhibit.model.Exhibition;
import com.exhibit.model.Hall;
import com.exhibit.model.services.ExhibitionService;
import com.exhibit.model.services.HallService;
import com.exhibit.model.services.ServiceFactory;

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