package com.exhibit.services;

import com.exhibit.dao.ExhibitionDao;
import com.exhibit.dao.UserDao;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Hall;
import com.exhibit.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ExhibitionServiceTest {

    ExhibitionService service;

    @BeforeEach
    void setUp() {
        service = new ExhibitionDao();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addExhibition() {
        String theme = "Test Theme" + randomString();
        String detail = "Some test details";
        Date date = Date.valueOf(LocalDate.now());
        Time time = new Time(7200000);
        double price = 100D;

        Exhibition expectedExh = Exhibition.newBuilder()
                .setTheme(theme)
                .setDetails(detail)
                .setStartDate(date)
                .setEndDate(date)
                .setStartTime(time)
                .setEndTime(time)
                .setPrice(price)
                .build();


        service.add(expectedExh);
        if (service.findById(expectedExh.getId()).isPresent()) {
            Exhibition actualExh = service.findById(expectedExh.getId()).get();
            assertEquals(expectedExh, actualExh);
        } else {
            fail();
        }

        String[] halls = {"1", "3", "5"};
        service.setHalls(expectedExh.getId(), halls);
        List<Hall> actualHalls = service.getHalls(expectedExh.getId());
        for (int i = 0; i < halls.length; i++) {
            assertEquals(actualHalls.get(i).getId(), Long.valueOf(halls[i]));
        }
    }


    @Test
    void numberOfExhibitions() {
        List<Exhibition> exhibitionList = service.findAll();
        Exhibition exhibition = exhibitionList.get(1);

        long expected = service.findAll().size() + 2;
        service.add(exhibition);
        service.add(exhibition);
        long actual = service.findAll().size();
        assertEquals(expected, actual);

    }

    @Test
    void amountOfTickets() {
        User user = new User(randomString(), randomString());
        UserService userService = new UserDao();
        userService.add(user);
        List<Exhibition> exhibitionList = service.findAll().subList(0,2);

        long expectedAmount = service.amountOfTickets(exhibitionList.get(0).getId()) + service.amountOfTickets(exhibitionList.get(1).getId()) + 2;

        userService.buyTicket(user, exhibitionList.get(0).getId());
        userService.buyTicket(user, exhibitionList.get(1).getId());

        long actualAmount = service.amountOfTickets(exhibitionList.get(0).getId()) + service.amountOfTickets(exhibitionList.get(1).getId());

        assertEquals(expectedAmount, actualAmount);

    }

    @Test
    void cancelExhibition() {
        String theme = "Test Theme" + randomString();
        String detail = "Some test details";
        Date date = Date.valueOf(LocalDate.now());
        Time time = new Time(7200000);
        double price = 100D;

        Exhibition expectedExh = Exhibition.newBuilder()
                .setTheme(theme)
                .setDetails(detail)
                .setStartDate(date)
                .setEndDate(date)
                .setStartTime(time)
                .setEndTime(time)
                .setPrice(price)
                .build();


        service.add(expectedExh);
        service.cancel(expectedExh.getId());

        if (service.findById(expectedExh.getId()).isPresent()) {
            double actualPrice = service.findById(expectedExh.getId()).get().getPrice();
            assertEquals(-1D, actualPrice);
        } else {
            fail();
        }

    }

    @Test
    void deleteExhibition() {
        String theme = "Test Theme" + randomString();
        String detail = "Some test details";
        Date date = Date.valueOf(LocalDate.now());
        Time time = new Time(7200000);
        double price = 100D;

        Exhibition expectedExh = Exhibition.newBuilder()
                .setTheme(theme)
                .setDetails(detail)
                .setStartDate(date)
                .setEndDate(date)
                .setStartTime(time)
                .setEndTime(time)
                .setPrice(price)
                .build();


        service.add(expectedExh);
        assertTrue(service.findById(expectedExh.getId()).isPresent());
        String[] halls = {"1", "3", "5"};
        service.setHalls(expectedExh.getId(), halls);

        service.delete(expectedExh.getId());
        assertFalse(service.findById(expectedExh.getId()).isPresent());

        List<Hall> hallListNull = service.getHalls(expectedExh.getId());
        assertTrue(hallListNull.isEmpty());

    }

    private String randomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}


