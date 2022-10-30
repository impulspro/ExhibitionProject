package com.exhibit.util.constants;

public class ExhibitionConstants {
    public static final String ADD_EXHIBITION_SQL =
            "INSERT INTO exhibition (theme, details, start_date, end_date, start_time, end_time, price)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_EXHIBITION_BY_THEME_SQL =
            "SELECT * FROM exhibition WHERE theme = ?";
    public static final String FIND_EXHIBITION_BY_ID =
            "SELECT * FROM exhibition WHERE id = ?";
    public static final String FIND_EXHIBITIONS_BY_HALL_ID_SQL =
            "SELECT * FROM exhibition INNER JOIN exhibition_halls" +
                    " ON exhibition.id = exhibition_halls.exhibition_id " +
                    "WHERE exhibition_halls.hall_id = ?";
    public static final String FIND_ALL_EXHIBITIONS_SQL =
            "SELECT * FROM exhibition ORDER BY start_date";
    public static final String SORT_BY_DATE_WHERE_SQL =
            "SELECT * FROM exhibition WHERE ? BETWEEN start_date AND end_date ORDER BY start_date LIMIT ? OFFSET ?";
    public static final String SORT_BY_HALL_WHERE_SQL =
            "SELECT * FROM exhibition INNER JOIN exhibition_halls" +
                    " ON exhibition.id = exhibition_halls.exhibition_id " +
                    "WHERE exhibition_halls.hall_id = ? LIMIT ? OFFSET ?";
    public static final String SORT_BY_DATE_SQL =
            "SELECT * FROM exhibition ORDER BY start_date LIMIT ? OFFSET ?";
    public static final String SORT_BY_THEME_SQL =
            "SELECT * FROM exhibition ORDER BY theme LIMIT ? OFFSET ?";
    public static final String SORT_BY_PRICE_SQL =
            "SELECT * FROM exhibition ORDER BY price LIMIT ? OFFSET ?";

    public static final String SORT_BY_DATE = "sortByDate";
    public static final String SORT_BY_HALL = "sortByHall";
    public static final String SORT_BY_PRICE = "sortByPrice";
    public static final String SORT_BY_THEME = "sortByTheme";
    public static final String DELETE_EXHIBITION_BY_ID_SQL =
            "DELETE FROM exhibition WHERE id = ?";
    public static final String UPDATE_EXHIBITION_PRICE_BY_ID_SQL =
            "UPDATE exhibition SET price = ? WHERE id = ?";
    public static final String SET_HALLS_SQL =
            "INSERT INTO exhibition_halls (exhibition_id, hall_id) VALUES(?, ?)";
    public static final String FIND_ALL_HALLS_SQL =
            "SELECT * FROM hall";
    public static final String FIND_HALL_BY_ID_SQL =
            "SELECT * FROM hall WHERE id = ?";

    public static final String FIND_EXHIBITIONS_AMOUNT_SQL =
            "SELECT COUNT(*) FROM exhibition";
    public static final String FIND_EXHIBITIONS_AMOUNT_WHERE_DATE_SQL =
            "SELECT COUNT(*) FROM exhibition WHERE ? BETWEEN start_date AND end_date";
    public static final String FIND_EXHIBITIONS_AMOUNT_WHERE_HALL_SQL =
            "SELECT COUNT(*) FROM exhibition INNER JOIN exhibition_halls" +
                    " ON exhibition.id = exhibition_halls.exhibition_id " +
                    "WHERE exhibition_halls.hall_id = ?";

    public static final String FIND_EXHIBITIONS_BY_HALL_SQL =
            "SELECT * FROM exhibition INNER JOIN exhibition_halls" +
                    " ON exhibition.id = exhibition_halls.exhibition_id " +
                    "WHERE exhibition_halls.hall_id = ?";
    public static final String FIND_HALLS_BY_EXHIBITION_ID =
            "SELECT * FROM hall INNER JOIN exhibition_halls" +
                    " ON hall.id = exhibition_halls.hall_id " +
                    "WHERE exhibition_halls.exhibition_id = ?";
    public static final String FIND_AMOUNT_OF_TICKETS_BY_EXHIBITION_ID_SQL =
            "SELECT COUNT(*) FROM user_tickets WHERE exhibition_id = ?";

    public static final String EXHIBITION_INPUT_FAILED = "Exhibition input failed";

    private ExhibitionConstants() {
    }
}
