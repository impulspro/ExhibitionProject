package com.exhibit.services;

import com.exhibit.dao.BasicConnectionManager;
import com.exhibit.dao.impl.UserDao;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Hall;
import com.exhibit.dao.model.User;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.HallService;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import com.exhibit.util.PasswordHashing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ExhibitionServiceTest {
    private static final String CONNECTION_URL = "jdbc:derby:memory:testExhibitionDb;create=true";

    private static final String SHUTDOWN_URL = "jdbc:derby:;shutdown=true";

    private static final String APP_PROPS_FILE = "app.properties";

    private static final String APP_CONTENT = "connection.url=" + CONNECTION_URL;

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE `user` (\n" +
                    "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                    "  `login` VARCHAR(32) NOT NULL,\n" +
                    "  `password` VARCHAR(32) NOT NULL,\n" +
                    "  `role` VARCHAR(32) NOT NULL,\n" +
                    "  `money` DECIMAL(9,2) UNSIGNED NOT NULL DEFAULT 1000,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
                    "  UNIQUE INDEX `username_UNIQUE` (`login` ASC) VISIBLE)\n" +
                    "ENGINE = InnoDB\n" +
                    "KEY_BLOCK_SIZE = 1;";


    private static final String CREATE_EXHIBITION_TABLE =
            "CREATE TABLE IF NOT EXISTS `exhibition` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `theme` VARCHAR(255) NOT NULL,\n" +
                    "  `details` VARCHAR(1024) NOT NULL,\n" +
                    "  `start_date` DATE NOT NULL,\n" +
                    "  `end_date` DATE NOT NULL,\n" +
                    "  `start_time` TIME NOT NULL,\n" +
                    "  `end_time` TIME NOT NULL,\n" +
                    "  `price` DECIMAL(9,2) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `exhibition_id_UNIQUE` (`id` ASC) VISIBLE,\n" +
                    "  FULLTEXT INDEX `idx_exhibition_theme` (`theme`) VISIBLE,\n" +
                    "  INDEX `idx_exhibtition_start_date` (`start_date` ASC) INVISIBLE,\n" +
                    "  INDEX `idx_exhibtition_price` (`price` ASC) VISIBLE);";

    private static final String CREATE_HALL_TABLE =
            "CREATE TABLE IF NOT EXISTS `hall` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(60) NOT NULL,\n" +
                    "  `details` VARCHAR(1024) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `hall_id_UNIQUE` (`id` ASC) VISIBLE);";

    private static final String CREATE_EXHIBITION_HALLS_TABLE =
            "CREATE TABLE IF NOT EXISTS `exhibition_halls` (\n" +
                    "  `exhibition_id` INT NOT NULL,\n" +
                    "  `hall_id` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`exhibition_id`, `hall_id`),\n" +
                    "  INDEX `fk_exhibition_has_hall_hall1_idx` (`hall_id` ASC) VISIBLE,\n" +
                    "  INDEX `fk_exhibition_has_hall_exhibition_idx` (`exhibition_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_exhibition_has_hall_exhibition`\n" +
                    "    FOREIGN KEY (`exhibition_id`)\n" +
                    "    REFERENCES `exhibition_db`.`exhibition` (`id`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `fk_exhibition_has_hall_hall1`\n" +
                    "    FOREIGN KEY (`hall_id`)\n" +
                    "    REFERENCES `exhibition_db`.`hall` (`id`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE);";

    private static final String CREATE_USER_TICKETS_TABLE =
            "CREATE TABLE IF NOT EXISTS `user_tickets` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `user_id` INT UNSIGNED NOT NULL,\n" +
                    "  `exhibition_id` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`, `user_id`, `exhibition_id`),\n" +
                    "  INDEX `fk_user_has_exhibition_exhibition1_idx` (`exhibition_id` ASC) VISIBLE,\n" +
                    "  INDEX `fk_user_has_exhibition_user1_idx` (`user_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_user_has_exhibition_user1`\n" +
                    "    FOREIGN KEY (`user_id`)\n" +
                    "    REFERENCES `exhibition_db`.`user` (`id`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `fk_user_has_exhibition_exhibition1`\n" +
                    "    FOREIGN KEY (`exhibition_id`)\n" +
                    "    REFERENCES `exhibition_db`.`exhibition` (`id`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE)\n" +
                    "ENGINE = InnoDB;";

    private static final String DROP_USER_TABLE = "DROP TABLE user";
    private static final String DROP_EXHIBITION_TABLE = "DROP TABLE exhibition";
    private static final String DROP_HALL_TABLE = "DROP TABLE hall";
    private static final String DROP_EXHIBITION_HALLS = "DROP TABLE exhibition_halls";
    private static final String DROP_USER_TICKETS_TABLE = "DROP TABLE user_tickets";

    private static final String DERBY_LOG_FILE = "derby.log";

    private static Connection con;

    private static String userDefinedAppContent;












    ExhibitionService exhibitionService;
    UserService userService;
    HallService hallService;
    @BeforeEach
    void setUp() {

        exhibitionService = ServiceFactory.getInstance().getExhibitionService(BasicConnectionManager.getInstance());
        userService = ServiceFactory.getInstance().getUserService(BasicConnectionManager.getInstance());
        hallService = ServiceFactory.getInstance().getHallService(BasicConnectionManager.getInstance());
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


        exhibitionService.add(expectedExh);
        if (exhibitionService.findById(expectedExh.getId()).isPresent()) {
            Exhibition actualExh = exhibitionService.findById(expectedExh.getId()).get();
            assertEquals(expectedExh, actualExh);
        } else {
            fail();
        }

        String[] halls = {"1", "3", "5"};
        hallService.setHallByExhibitionId(expectedExh.getId(), halls);
        List<Hall> actualHalls = hallService.getHallsByExhibitionId(expectedExh.getId());
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


        long expected = exhibitionService.findAll().size() + 2;
        exhibitionService.add(expectedExh);
        expectedExh.setTheme(randomString());
        exhibitionService.add(expectedExh);

        long actual = exhibitionService.findAll().size();
        assertEquals(expected, actual);

    }

    @Test
    void amountOfTickets() {
        User user = new User(randomString(), PasswordHashing.toMD5(randomString()));
        userService.add(user);
        List<Exhibition> exhibitionList = exhibitionService.findAll().subList(0,2);

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

}


