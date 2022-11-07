package com.exhibit.services;

import com.exhibit.dao.connection.BasicConnectionManager;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Ticket;
import com.exhibit.dao.model.User;
import com.exhibit.util.PasswordHashing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.exhibit.dao.constants.UserConstants.BUY_TICKET_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserServiceTest {
    UserService userService;
    ExhibitionService exhibitionService;

    @BeforeAll
    void globalSetUp(){
        userService = ServiceFactory.getInstance().getUserService(BasicConnectionManager.getInstance());
        exhibitionService = ServiceFactory.getInstance().getExhibitionService(BasicConnectionManager.getInstance());
    }

    @BeforeEach
    void setUp() throws SQLException {

    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void addUser() {
        String login = randomString();
        String password = PasswordHashing.toMD5(randomString());
        Optional<User> expectedUser = Optional.of(new User(login, password));

        List<User> userListExpected = userService.findAll();
        userService.add(expectedUser.get());
        userListExpected.add(expectedUser.get());

        Optional<User> actualUser = userService.findByLogin(login);
        assertEquals(expectedUser, actualUser);

        List<User> userListActual = userService.findAll();
        assertEquals(userListExpected, userListActual);
    }

    @Test
    void deleteUser() {
        String login = randomString();
        String password = PasswordHashing.toMD5(randomString());
        Optional<User> expectedUser = Optional.of(new User(login, password));

        List<User> userListExpected = userService.findAll();
        userService.add(expectedUser.get());
        userListExpected.add(expectedUser.get());

        Optional<User> actualUser = userService.findByLogin(login);
        assertEquals(expectedUser, actualUser);

        List<User> userListActual = userService.findAll();
        assertEquals(userListExpected, userListActual);

        userService.delete(expectedUser.get());
        Optional<User> deletedUser = userService.findByLogin(expectedUser.get().getLogin());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void buyTicket() {
        String login = randomString();
        String password = PasswordHashing.toMD5(randomString());
        Optional<User> user = Optional.of(new User(login, password));

        userService.add(user.get());
        List<Exhibition> exhibitionList = exhibitionService.findAll();
        for (int i = 0; i < 3; i++) {
            String answer = userService.buyTicket(user.get(), exhibitionList.get(i).getId());
            assertEquals(BUY_TICKET_OK, answer);
        }


        List<Ticket> ticketList = userService.getUserTickets(user.get().getId());
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