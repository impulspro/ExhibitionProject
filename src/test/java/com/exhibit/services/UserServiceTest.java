package com.exhibit.services;

import com.exhibit.dao.ExhibitionDao;
import com.exhibit.dao.UserDao;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Ticket;
import com.exhibit.model.User;
import com.exhibit.util.PasswordHashing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService service;

    @BeforeEach
    void setUp() {
        service = new UserDao();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addUser() {
        String login = randomString();
        String password = PasswordHashing.toMD5(randomString());
        Optional<User> expectedUser = Optional.of(new User(login, password));

        List<User> userListExpected = service.findAll();
        service.add(expectedUser.get());
        userListExpected.add(expectedUser.get());

        Optional<User> actualUser = service.findByLogin(login);
        assertEquals(expectedUser, actualUser);

        List<User> userListActual = service.findAll();
        assertEquals(userListExpected, userListActual);
    }

    @Test
    void deleteUser() {
        String login = randomString();
        String password = PasswordHashing.toMD5(randomString());
        Optional<User> expectedUser = Optional.of(new User(login, password));

        List<User> userListExpected = service.findAll();
        service.add(expectedUser.get());
        userListExpected.add(expectedUser.get());

        Optional<User> actualUser = service.findByLogin(login);
        assertEquals(expectedUser, actualUser);

        List<User> userListActual = service.findAll();
        assertEquals(userListExpected, userListActual);

        service.delete(expectedUser.get());
        Optional<User> deletedUser = service.findByLogin(expectedUser.get().getLogin());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void buyTicket() {
        String login = randomString();
        String password = PasswordHashing.toMD5(randomString());
        Optional<User> user = Optional.of(new User(login, password));

        service.add(user.get());
        ExhibitionService exhibitionService = new ExhibitionDao();
        List<Exhibition> exhibitionList = exhibitionService.findAll();
        for (int i = 0; i < 3; i++) {
            String answer = service.buyTicket(user.get(), exhibitionList.get(i).getId());
            assertEquals("ok", answer);
        }


        List<Ticket> ticketList = service.getUserTickets(user.get());
        for (int i = 0; i < ticketList.size(); i++) {
            assertEquals(ticketList.get(i).getExhibitionId(), exhibitionList.get(i).getId());
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