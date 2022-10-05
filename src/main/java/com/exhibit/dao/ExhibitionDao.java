package com.exhibit.dao;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.exeptions.DBException;
import com.exhibit.model.Exhibition;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.util.constants.ExhibitionConstants.*;


public class ExhibitionDao {
    static Mapper mapper = MapperFactory.getInstance().getExhibitionMapper();

    public static void add(Exhibition exhibition) throws DBException {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(ADD_EXHIBITION_SQL)) {
            int i = 1;
            prepSt.setString(i++, exhibition.getTheme());
            prepSt.setString(i++, exhibition.getDetails());
            prepSt.setDate(i++, exhibition.getStartDate());
            prepSt.setDate(i++, exhibition.getEndDate());
            prepSt.setTime(i++, exhibition.getStartTime());
            prepSt.setTime(i++, exhibition.getEndTime());
            prepSt.setDouble(i++, exhibition.getPrice());
            prepSt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Invalid exhibition input", e);
        }
    }
    public static Exhibition findById(long id) throws DBException {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(FIND_EXHIBITION_BY_THEME_ID)) {
            prepSt.setLong(1, id);
            ResultSet rs = prepSt.executeQuery();
            if (rs.next()) {
                return (Exhibition) mapper.extractFromResultSet(rs);
            }
        } catch (SQLException | DBException e) {
            throw new DBException("Cannot find exhibition by id " + id, e);
        }
        return null;
    }
    public static Exhibition findByTheme(String theme) throws DBException {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(FIND_EXHIBITION_BY_THEME_SQL)) {
            prepSt.setString(1, theme);
            ResultSet rs = prepSt.executeQuery();
            if (rs.next()) {
                return (Exhibition) mapper.extractFromResultSet(rs);
            }
        } catch (SQLException | DBException e) {
            throw new DBException("Cannot find exhibition by theme " + theme, e);
        }
        return null;
    }

    public static List<Exhibition> findAll() throws DBException {
        List<Exhibition> exhibitions = null;
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(FIND_ALL_EXHIBITIONS_SQL)) {
            ResultSet rs = prepSt.executeQuery();
            if (rs.next()) {
                exhibitions.add((Exhibition) mapper.extractFromResultSet(rs));
            }
        } catch (SQLException | DBException e) {
            throw new DBException("Cannot find all exhibition", e);
        }
        return exhibitions;
    }
    public static void setHalls(long exhibition_id, String[] halls_id) throws DBException {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(SET_HALLS_SQL)) {
            prepSt.setLong(1, exhibition_id);
            ResultSet rs = prepSt.executeQuery();
            for (String hall_id : halls_id) {
                prepSt.setLong(2, Long.parseLong(hall_id));
                prepSt.executeUpdate();
            }
        } catch (SQLException | DBException e) {
            throw new DBException("Cannot set halls for exhibition " + exhibition_id, e);
        }
    }

    public void delete(long id) throws DBException {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(DELETE_EXHIBITION_BY_ID_SQL)) {
            prepSt.setLong(1, id);
            prepSt.executeUpdate();
        } catch (SQLException | DBException e) {
            throw new DBException("Cannot delete exhibition " + id, e);
        }
    }
}
