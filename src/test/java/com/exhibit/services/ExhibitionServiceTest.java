package com.exhibit.services;

import com.exhibit.dao.impl.UserDao;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Hall;
import com.exhibit.dao.model.User;
import com.exhibit.util.PasswordHashing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.exhibit.util.constants.UtilConstants.RECORDS_PER_PAGE;
import static org.junit.jupiter.api.Assertions.*;

class ExhibitionServiceTest {

    ExhibitionService service;

    @BeforeEach
    void setUp() {
        service = ServiceFactory.getInstance().getExhibitionService();
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
        HallService hallService = ServiceFactory.getInstance().getHallService();
        hallService.setHallByExhibitionId(expectedExh.getId(), halls);
        List<Hall> actualHalls = hallService.getHallByExhibitionId(expectedExh.getId());
        for (int i = 0; i < halls.length; i++) {
            assertEquals(actualHalls.get(i).getId(), Long.valueOf(halls[i]));
        }
    }


    @Test
    void numberOfExhibitions() {
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


        long expected = service.findAll().size() + 2;
        service.add(expectedExh);
        expectedExh.setTheme(randomString());
        service.add(expectedExh);

        long actual = service.findAll().size();
        assertEquals(expected, actual);

    }

    @Test
    void findByDatePerPage(){
        List<Exhibition> exhibitions = service.findAll();
        Date date = exhibitions.get(1).getStartDate();

        List<Exhibition> exhibitionListExpected = exhibitions.stream().filter(e -> (e.getStartDate().before(date) || e.getStartDate().equals(date))
                && (e.getEndDate().after(date) || e.getEndDate().equals(date))).collect(Collectors.toList());

        List<Exhibition> exhibitionListActual = new CopyOnWriteArrayList<>();
        int noOfPages = (int) Math.ceil(exhibitionListExpected.size() * 1.0 / RECORDS_PER_PAGE);
        for (int i = 1; i <= noOfPages; i++) {
            exhibitionListExpected.addAll(service.findByDatePerPage(date,1));
        }

        assertEquals(exhibitionListExpected, exhibitionListActual);
    }
    @Test
    void amountOfTickets() {
        User user = new User(randomString(), PasswordHashing.toMD5(randomString()));
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
        HallService hallService = ServiceFactory.getInstance().getHallService();
        hallService.setHallByExhibitionId(expectedExh.getId(), halls);

        service.delete(expectedExh.getId());
        assertFalse(service.findById(expectedExh.getId()).isPresent());

        List<Hall> hallListNull = hallService.getHallByExhibitionId(expectedExh.getId());
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


