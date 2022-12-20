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
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.dao.constants.ExhibitionConstants.SORT_BY_DATE;
import static com.exhibit.dao.constants.ExhibitionConstants.SORT_BY_HALL;
import static com.exhibit.dao.constants.UserConstants.USER_DEFAULT_MONEY;
import static com.exhibit.dao.constants.UtilConstants.RECORDS_PER_PAGE;
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
        testIterations = 8;
        hallsAmount = 8;
        date = Date.valueOf("2025-08-01");
        detail = "Some test details";
        price = 100D;
        time = new Time(7200000);
    }

    @BeforeEach
    void cleanDB() {
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
    void sortByDate() {
        List<Exhibition> exhibitionsPage1 = new CopyOnWriteArrayList<>();
        List<Exhibition> exhibitionsPage2 = new CopyOnWriteArrayList<>();
        Date date = nextDate();
        for (int i = 0; i < testIterations; i++) {
            String theme = "Test Theme " + randomString();
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
            if (i < RECORDS_PER_PAGE) {
                exhibitionsPage1.add(exhibition);
            }
            if (i >= RECORDS_PER_PAGE && i < RECORDS_PER_PAGE * 2) {
                exhibitionsPage2.add(exhibition);
            }
        }

        List<Exhibition> exhibitionsActual = exhibitionService.findSortByWhereIs(SORT_BY_DATE, String.valueOf(date), 1);
        assertEquals(exhibitionsPage1, exhibitionsActual);
        exhibitionsActual = exhibitionService.findSortByWhereIs(SORT_BY_DATE, String.valueOf(date), 2);
        assertEquals(exhibitionsPage2, exhibitionsActual);
    }

    @Test
    void sortByHall() {
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
            hallService.setHallByExhibitionId(exhibition.getId(), new String[]{"1", "2"});
        }

        long amountOfPages = exhibitionService.amountOfExhibitions(SORT_BY_HALL, "1");
        List<Exhibition> allExhibitions = new CopyOnWriteArrayList<>();
        for (int i = 1; i < amountOfPages + 1; i++) {
            allExhibitions.addAll(exhibitionService.findSortByWhereIs(SORT_BY_HALL, "1", i));
        }

        List<Exhibition> exhibitionsExpected = new CopyOnWriteArrayList<>();
        for (Exhibition exh : allExhibitions) {
            List<Hall> hallList = hallService.getHallsByExhibitionId(exh.getId());
            for (Hall hall : hallList) {
                if (hall.getId() == 1) {
                    exhibitionsExpected.add(exh);
                }
            }
        }

        List<Exhibition> exhibitionsActual = new CopyOnWriteArrayList<>();
        for (int i = 0; i < amountOfPages; i++) {
            List<Exhibition> pageExhibitions = exhibitionService.findSortByWhereIs(SORT_BY_HALL, "1", i + 1);
            exhibitionsActual.addAll(pageExhibitions);
        }

        assertEquals(exhibitionsExpected, exhibitionsActual);
    }

    @Test
    void amountOfExhibitions() {
        int exhibitionsExpectedCount = exhibitionService.findAllActual().size();
        Date date = nextDate();
        for (int i = 0; i < testIterations; i++) {
            String theme = "Test Theme " + randomString();
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

        int exhibitionsActualCount = exhibitionService.amountOfExhibitions();
        assertEquals(exhibitionsExpectedCount, exhibitionsActualCount);

        exhibitionsActualCount = exhibitionService.amountOfExhibitions(SORT_BY_DATE, String.valueOf(date));
        exhibitionsExpectedCount = testIterations;

        assertEquals(exhibitionsExpectedCount, exhibitionsActualCount);
        date = nextDate();

        exhibitionsActualCount = exhibitionService.amountOfExhibitions(SORT_BY_DATE, String.valueOf(date));
        assertEquals(0, exhibitionsActualCount);
    }

    @Test
    void amountOfTickets() {
        List<Exhibition> exhibitionList = exhibitionService.findAll().subList(0, 2);
        long expectedAmount = exhibitionService.amountOfTicketsByExhibition(exhibitionList.get(0).getId())
                + exhibitionService.amountOfTicketsByExhibition(exhibitionList.get(1).getId())
                + 2L * testIterations;

        for (int i = 0; i < testIterations; i++) {
            User user = new User(randomString(), PasswordHashing.toMD5(randomString()));
            userService.add(user);
            userService.buyTicket(user, exhibitionList.get(0).getId());
            userService.buyTicket(user, exhibitionList.get(1).getId());
        }

        long actualAmount = exhibitionService.amountOfTicketsByExhibition(exhibitionList.get(0).getId())
                + exhibitionService.amountOfTicketsByExhibition(exhibitionList.get(1).getId());

        assertEquals(expectedAmount, actualAmount);
    }

    @Test
    void cancelExhibition() {
        String theme = "Test Theme" + randomString();
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

        // Assert that users accounts have decreased
        double moneyExpected = (USER_DEFAULT_MONEY - exhibition.getPrice()) * testIterations;
        double moneyActual = 0;
        List<User> users = new CopyOnWriteArrayList<>();
        for (int i = 0; i < testIterations; i++) {
            User user = new User(randomString(), PasswordHashing.toMD5(randomString()));
            userService.add(user);
            userService.buyTicket(user, exhibition.getId());
            moneyActual += user.getMoney();
            users.add(userService.findByLogin(user.getLogin()).get());
        }
        assertEquals(moneyExpected, moneyActual);

        exhibitionService.cancel(exhibition.getId());

        if (exhibitionService.findById(exhibition.getId()).isPresent()) {
            double actualPrice = exhibitionService.findById(exhibition.getId()).get().getPrice();
            assertEquals(-1D, actualPrice);
        } else {
            fail();
        }

        // Assert that users accounts have refunded
        moneyExpected = USER_DEFAULT_MONEY * testIterations;
        moneyActual = 0;
        for (int i = 0; i < testIterations; i++) {
            moneyActual += userService.findByLogin(users.get(i).getLogin()).get().getMoney();
        }
        assertEquals(moneyExpected, moneyActual);
    }

    @Test
    void deleteExhibition() {
        String theme = "Test Theme" + randomString();
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
        assertTrue(exhibitionService.findById(exhibition.getId()).isPresent());

        String[] halls = {"1", "3", "5"};
        hallService.setHallByExhibitionId(exhibition.getId(), halls);
        List<Hall> hallList = hallService.getHallsByExhibitionId(exhibition.getId());
        assertFalse(hallList.isEmpty());

        // Assert that users accounts have decreased
        double moneyExpected = (USER_DEFAULT_MONEY - exhibition.getPrice()) * testIterations;
        double moneyActual = 0;
        List<User> users = new CopyOnWriteArrayList<>();
        for (int i = 0; i < testIterations; i++) {
            User user = new User(randomString(), PasswordHashing.toMD5(randomString()));
            userService.add(user);
            userService.buyTicket(user, exhibition.getId());
            moneyActual += user.getMoney();
            users.add(userService.findByLogin(user.getLogin()).get());
        }
        assertEquals(moneyExpected, moneyActual);


        exhibitionService.delete(exhibition.getId());
        assertFalse(exhibitionService.findById(exhibition.getId()).isPresent());
        List<Hall> hallListNull = hallService.getHallsByExhibitionId(exhibition.getId());
        assertTrue(hallListNull.isEmpty());

        // Assert that users accounts have refunded
        moneyExpected = USER_DEFAULT_MONEY * testIterations;
        moneyActual = 0;
        for (int i = 0; i < testIterations; i++) {
            moneyActual += userService.findByLogin(users.get(i).getLogin()).get().getMoney();
        }
        assertEquals(moneyExpected, moneyActual);
    }

    @Test
    void checkExhibitionsInPast() {
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

        Optional<Exhibition> checkExhibition = exhibitionService.findById(exhibition.getId());
        assertFalse(exhibitionService.inPast(checkExhibition.get().getId()));
        assertTrue(exhibitionService.isTicketCanBeReturnByExhibition(checkExhibition.get().getId()));

        User user = new User(randomString(), PasswordHashing.toMD5(randomString()));
        userService.add(user);

        //Exhibition in the past
        theme = "Test Theme " + randomString();
        date = Date.valueOf("2020-01-01");
        exhibition = Exhibition.newBuilder()
                .setTheme(theme)
                .setDetails(detail)
                .setStartDate(date)
                .setEndDate(date)
                .setStartTime(time)
                .setEndTime(time)
                .setPrice(price)
                .build();
        exhibitionService.add(exhibition);
        checkExhibition = exhibitionService.findById(exhibition.getId());
        assertTrue(exhibitionService.inPast(checkExhibition.get().getId()));
        assertFalse(exhibitionService.isTicketCanBeReturnByExhibition(checkExhibition.get().getId()));

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


