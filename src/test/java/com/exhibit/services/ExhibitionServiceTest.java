package com.exhibit.services;

import com.exhibit.dao.connection.TestConnectionManager;
import com.exhibit.dao.connection.TestDB;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Hall;
import com.exhibit.dao.model.User;
import com.exhibit.util.PasswordHashing;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExhibitionServiceTest {
    static ExhibitionService exhibitionService;
    static UserService userService;
    static HallService hallService;
    static int testIterations;
    static Date date;
    static String detail;
    static Time time;
    static double price;
    static int hallsAmount;


    @BeforeAll
    static void globalSetUp() {
        exhibitionService = ServiceFactory.getInstance().getExhibitionService(TestConnectionManager.getInstance());
        userService = ServiceFactory.getInstance().getUserService(TestConnectionManager.getInstance());
        hallService = ServiceFactory.getInstance().getHallService(TestConnectionManager.getInstance());
        testIterations = 6;
        hallsAmount = 8;
        date = Date.valueOf("2023-06-01");
        detail = "Some test details";
        price = 100D;
        time = new Time(7200000);
    }

    @BeforeEach
    void cleanDB(){
        TestDB.restartDBScript();
    }

    @Test
    void addExhibitionWithoutHalls() {
        List<Exhibition> exhibitionsExpected = exhibitionService.findAll();
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
            exhibitionsExpected.add(exhibition);
        }

        List<Exhibition> exhibitionsActual = exhibitionService.findAll();
        assertEquals(exhibitionsExpected, exhibitionsActual);
    }

    @Test
    void setHallsForExhibition() {
        List<Exhibition> exhibitionsExpected = new CopyOnWriteArrayList<>();
        String[][] hallsExpected = new String[testIterations][];
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
            exhibitionsExpected.add(exhibition);

            int hall1 = (int) (Math.random() * (hallsAmount - 2)) + 1;
            int hall2 = hall1 + 1;
            String[] halls = {String.valueOf(hall1), String.valueOf(hall2)};
            hallService.setHallByExhibitionId(exhibition.getId(), halls);
            hallsExpected[i] = halls;
        }

        for (int i = 0; i < testIterations; i++) {
            long exhibitionId = exhibitionsExpected.get(i).getId();
            List<Hall> hallsActual = hallService.getHallsByExhibitionId(exhibitionId);
            assertEquals(hallsExpected[i][0], String.valueOf(hallsActual.get(0).getId()));
            assertEquals(hallsExpected[i][1], String.valueOf(hallsActual.get(1).getId()));
        }
    }

    @Test
    void numberOfExhibitions() {
        int exhibitionsExpectedCount = exhibitionService.findAll().size();
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
            exhibitionsExpectedCount++;
        }

        int exhibitionsActualCount = exhibitionService.findAll().size();
        assertEquals(exhibitionsExpectedCount, exhibitionsActualCount);
    }

    @Test
    void amountOfTickets() {


        User user = new User(randomString(), PasswordHashing.toMD5(randomString()));
        userService.add(user);
        List<Exhibition> exhibitionList = exhibitionService.findAll().subList(0, 2);

        long expectedAmount = exhibitionService.amountOfTicketsByExhibition(exhibitionList.get(0).getId()) + exhibitionService.amountOfTicketsByExhibition(exhibitionList.get(1).getId()) + 2;

        userService.buyTicket(user, exhibitionList.get(0).getId());
        userService.buyTicket(user, exhibitionList.get(1).getId());

        long actualAmount = exhibitionService.amountOfTicketsByExhibition(exhibitionList.get(0).getId()) + exhibitionService.amountOfTicketsByExhibition(exhibitionList.get(1).getId());

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


        exhibitionService.add(expectedExh);
        exhibitionService.cancel(expectedExh.getId());

        if (exhibitionService.findById(expectedExh.getId()).isPresent()) {
            double actualPrice = exhibitionService.findById(expectedExh.getId()).get().getPrice();
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


        exhibitionService.add(expectedExh);
        assertTrue(exhibitionService.findById(expectedExh.getId()).isPresent());
        String[] halls = {"1", "3", "5"};
        hallService.setHallByExhibitionId(expectedExh.getId(), halls);

        exhibitionService.delete(expectedExh.getId());
        assertFalse(exhibitionService.findById(expectedExh.getId()).isPresent());

        List<Hall> hallListNull = hallService.getHallsByExhibitionId(expectedExh.getId());
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

    Date nextDate() {
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


