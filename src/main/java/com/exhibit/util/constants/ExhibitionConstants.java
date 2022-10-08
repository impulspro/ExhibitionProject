package com.exhibit.util.constants;

public class ExhibitionConstants {
    private ExhibitionConstants() {
    }

    public static final String ADD_EXHIBITION_SQL =
            "INSERT INTO exhibition VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?";
    public static final String FIND_EXHIBITION_BY_THEME_SQL =
            "SELECT * FROM exhibition WHERE theme = ?";

    public static final String FIND_EXHIBITION_BY_THEME_ID =
            "SELECT * FROM exhibition WHERE id = ?";
    public static final String FIND_ALL_EXHIBITIONS_SQL =
            "SELECT * FROM exhibition";
    public static final String DELETE_EXHIBITION_BY_ID_SQL =
            "DELETE FROM exhibition WHERE id = ?";
    public static final String SET_HALLS_SQL =
            "INSERT INTO exhibitions_halls VALUES(?, ?)";

    public static final String FIND_ALL_HALLS_SQL =
            "SELECT * FROM hall";
}
