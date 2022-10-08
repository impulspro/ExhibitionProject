package com.exhibit;

import com.exhibit.dao.DaoException;
import com.exhibit.dao.UserDao;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Hall;
import com.exhibit.model.User;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.HallService;
import com.exhibit.services.UserService;

import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {
        ExhibitionService service = new ExhibitionService();
        List<Exhibition> halls = service.findAll();
        halls.stream().forEach(s -> System.out.println(s));
       // halls.stream().forEach(s -> System.out.println(s));
        /*List<Exhibition> exhibitions = service.findAll();
        exhibitions.stream().forEach(s -> System.out.println(s));
*/
    }
}
