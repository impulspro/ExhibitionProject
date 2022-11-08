package com.exhibit.services;

import com.exhibit.dao.connection.TestConnectionManager;
import com.exhibit.dao.connection.TestDB;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Ticket;
import com.exhibit.dao.model.User;
import com.exhibit.util.PasswordHashing;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.exhibit.dao.constants.UserConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    static ExhibitionService exhibitionService;
    static UserService userService;
    static HallService hallService;
    static int testIterations;

    @BeforeAll
    static void globalSetUp() {
        exhibitionService = ServiceFactory.getInstance().getExhibitionService(TestConnectionManager.getInstance());
        userService = ServiceFactory.getInstance().getUserService(TestConnectionManager.getInstance());
        hallService = ServiceFactory.getInstance().getHallService(TestConnectionManager.getInstance());
        testIterations = 8;
    }

    @BeforeEach
    void cleanDB() {
        TestDB.restartDBScript();
    }

    @Test
    void addUser() {
        List<User> userListExpected = userService.findAll();
        for (int i = 0; i < testIterations; i++) {
            User expectedUser = new User(randomString(), PasswordHashing.toMD5(randomString()));
            userService.add(expectedUser);
            userListExpected.add(expectedUser);
            Optional<User> actualUser = userService.findByLogin(expectedUser.getLogin());
            assertEquals(expectedUser, actualUser.get());
        }
        List<User> userListActual = userService.findAll();
        assertEquals(userListExpected, userListActual);
    }

    @Test
    void deleteUser() {
        List<User> userListExpected = userService.findAll();
        for (int i = 0; i < testIterations; i++) {
            User expectedUser = new User(randomString(), PasswordHashing.toMD5(randomString()));
            userService.add(expectedUser);
            userListExpected.add(expectedUser);
            Optional<User> actualUser = userService.findByLogin(expectedUser.getLogin());
            assertEquals(expectedUser, actualUser.get());
        }
        List<User> userListActual = userService.findAll();
        assertEquals(userListExpected, userListActual);

        for (int i = 0; i < testIterations; i++) {
            User user = userListExpected.get(userListActual.size() - i - 1);
            userService.delete(user.getId());
            Optional<User> checkUser = userService.findByLogin(user.getLogin());
            assertFalse(checkUser.isPresent());
        }
    }

    @Test
    void updateUser() {
        List<User> userListExpected = userService.findAll();
        for (int i = 0; i < testIterations; i++) {
            User expectedUser = new User(randomString(), PasswordHashing.toMD5(randomString()));
            userService.add(expectedUser);
            expectedUser.setMoney(2000.0);
            userService.update(expectedUser);
            userListExpected.add(expectedUser);
            Optional<User> actualUser = userService.findByLogin(expectedUser.getLogin());
            assertEquals(expectedUser, actualUser.get());
        }
        List<User> userListActual = userService.findAll();
        assertEquals(userListExpected, userListActual);
    }

    @Test
    void buyTicket() {
        List<Exhibition> exhibitionList = exhibitionService.findAll();

        for (int i = 0; i < testIterations; i++) {
            User user = new User(randomString(), PasswordHashing.toMD5(randomString()));
            userService.add(user);
            String answer = null;
            //Assert different returns of method buyTicket
            for (int j = 0; j < 3; j++) {
                answer = userService.buyTicket(user, exhibitionList.get(j).getId());
                assertEquals(BUY_TICKET_OK, answer);
                answer = userService.buyTicket(user, 2000L);
                assertEquals(NO_EXHIBITION_FOUND, answer);
                answer = userService.buyTicket(user, exhibitionList.get(j).getId());
                assertEquals(ALREADY_BOUGHT_TICKET, answer);
            }

            //Assert that user has tickets
            List<Ticket> ticketList = userService.getUserTickets(user.getId());
            for (int j = 0; j < ticketList.size(); j++) {
                assertEquals(ticketList.get(j).getExhibitionId(), exhibitionList.get(j).getId());
                assertTrue(userService.isTicketPresent(user.getLogin(), exhibitionList.get(j).getId()));
            }

            //Assert NO_ENOUGH_MONEY return of method buyTicket
            for (int j = 3; j < 8; j++) {
                answer = userService.buyTicket(user, exhibitionList.get(j).getId());
            }
            assertEquals(NO_ENOUGH_MONEY, answer);
        }

    }

    String randomString() {
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