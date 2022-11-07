package com.exhibit.dao.constants;

public class TestConstants {
    public static final String CONNECTION_URL = "jdbc:sqlite:memory:testDB;create=true";

    public static final String SHUTDOWN_URL = "jdbc:derby:;shutdown=true";

    public static final String CREATE_USER_TABLE =
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


    public static final String CREATE_EXHIBITION_TABLE =
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

    public static final String CREATE_HALL_TABLE =
            "CREATE TABLE IF NOT EXISTS `hall` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(60) NOT NULL,\n" +
                    "  `details` VARCHAR(1024) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `hall_id_UNIQUE` (`id` ASC) VISIBLE);";

    public static final String CREATE_EXHIBITION_HALLS_TABLE =
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

    public static final String CREATE_USER_TICKETS_TABLE =
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

    public static final String DROP_USER_TABLE = "DROP TABLE user";
    public static final String DROP_EXHIBITION_TABLE = "DROP TABLE exhibition";
    public static final String DROP_HALL_TABLE = "DROP TABLE hall";
    public static final String DROP_EXHIBITION_HALLS_TABLE = "DROP TABLE exhibition_halls";
    public static final String DROP_USER_TICKETS_TABLE = "DROP TABLE user_tickets";

    public static final String DERBY_LOG_FILE = "derby.log";

    public static final String INSERT_EXHIBITION_TABLE =
            "START TRANSACTION;\n" +
                    "USE `exhibition_db`;\n" +
                    "INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin', 0);\n" +
                    "INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'Jimmy', '7c6a180b36896a0a8c02787eeafb0e4c', 'user', 1200);\n" +
                    "INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'Tom', '6cb75f652a9b52798eb6cf2201057c73', 'user', 1000);\n" +
                    "INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'Василий', '819b0643d6b89dc9b579fdfc9094f28e', 'user', 1000);\n" +
                    "INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'Степан', '218dd27aebeccecae69ad8408d9a36bf', 'user', 1000);\n" +
                    "INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'Дмитро', '218dd27aebeccecae69ad8408d9a363f', 'user', 1500);\n" +
                    "INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'user1', '96e79218965eb72c92a549dd5a330112', 'user', 800);\n" +
                    "INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'user2', 'e3ceb5881a0a1fdaad01296d7554868d', 'user', 980);\n" +
                    "INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'user3', '1a100d2c0dab19c4430e7d73762b3423', 'user', 560);\n" +
                    "\n" +
                    "COMMIT;";
    private TestConstants(){}
}
