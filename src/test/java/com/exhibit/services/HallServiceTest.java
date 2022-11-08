package com.exhibit.services;

import com.exhibit.dao.connection.TestConnectionManager;
import com.exhibit.dao.connection.TestDB;
import com.exhibit.dao.model.Exhibition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HallServiceTest {

    static ExhibitionService exhibitionService;
    static UserService userService;
    static HallService hallService;
    static int testIterations;
    static int hallsAmount;
    static Date date;
    static String detail;
    static Time time;
    static double price;

    @BeforeAll
    static void globalSetUp() {
        exhibitionService = ServiceFactory.getInstance().getExhibitionService(TestConnectionManager.getInstance());
        userService = ServiceFactory.getInstance().getUserService(TestConnectionManager.getInstance());
        hallService = ServiceFactory.getInstance().getHallService(TestConnectionManager.getInstance());
        testIterations = 8;
        hallsAmount = 8;
        date = Date.valueOf("2023-06-01");
        detail = "Some test details";
        price = 100D;
        time = new Time(7200000);
    }

    @BeforeEach
    void cleanDB() {
        TestDB.restartDBScript();
    }

    @Test
    void findAll() {
        assertEquals(hallsAmount, hallService.findAll().size());
    }

    @Test
    void findById() {
        for (int i = 1; i <= hallsAmount; i++) {
            assertEquals(i, hallService.findById(i).get().getId());
        }
    }

    @Test
    void findAllExhibitionsByHall() {
        List<Exhibition> exhibitionListExpected = hallService.findAllExhibitionsByHall(1);
        for (int i = 0; i < testIterations; i++) {
            String theme = "Test Theme " + randomString();
            Date date = nextDate();

            Exhibition exhibition = Exhibition.newBuilder()
                    .setTheme(theme)
                    .setDetails(detail)
                    .setStartDate(date)
                    .setEndDate(date)
                    .setStartTime(time)
                    .setEndTime(time)
                    .setPrice(price)
                    .build();
            exhibitionService.add(exhibition);
            hallService.setHallByExhibitionId(exhibition.getId(), new String[]{"1"});
            exhibitionListExpected.add(exhibition);
        }
        List<Exhibition> exhibitionListActual = hallService.findAllExhibitionsByHall(1);
        assertEquals(exhibitionListExpected, exhibitionListActual);
    }

    @Test
    void isOccupiedBetweenTwoDates() {
        for (int i = 0; i < testIterations; i++) {
            String theme = "Test Theme " + randomString();
            Date startDate = nextDate();
            Date endDate = nextDate();

            Exhibition exhibition = Exhibition.newBuilder()
                    .setTheme(theme)
                    .setDetails(detail)
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .setStartTime(time)
                    .setEndTime(time)
                    .setPrice(price)
                    .build();
            exhibitionService.add(exhibition);
            hallService.setHallByExhibitionId(exhibition.getId(), new String[]{"1"});
            assertTrue(hallService.isOccupiedBetweenTwoDates(1, startDate, endDate));
            assertFalse(hallService.isOccupiedBetweenTwoDates(1, nextDate(), nextDate()));
        }
    }

    //method for generating random Theme
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

    //method for generating next Date
    private Date nextDate() {
        String dt = String.valueOf(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        c.add(Calendar.DATE, 3);  // number of days to add
        dt = sdf.format(c.getTime());
        date = Date.valueOf(dt);
        return date;
    }

}