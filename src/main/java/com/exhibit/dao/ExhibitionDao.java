package com.exhibit.dao;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.exeptions.DBException;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Hall;
import com.exhibit.services.HallService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

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

    public Exhibition findById(long id) throws DBException {
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

    public Exhibition findByTheme(String theme) throws DBException {
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

    public List<Exhibition> findAll() throws DBException {
        List<Exhibition> exhibitions = new CopyOnWriteArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement psExs = conn.prepareStatement(FIND_ALL_EXHIBITIONS_SQL);
             PreparedStatement psHalls = conn.prepareStatement(FIND_EXHIBITIONS_RELATED_HALLS_SQL)) {
            ResultSet rsExs = psExs.executeQuery();
            while (rsExs.next()) {
                exhibitions.add((Exhibition) mapper.extractFromResultSet(rsExs));
            }

            ResultSet rsHalls = psHalls.executeQuery();
            HashMap<Long, Long> exHallsMap = new HashMap<>();
            while (rsHalls.next()) {
                long exhibition_id = rsHalls.getLong(1);
                long hall_id = rsHalls.getLong(2);
                exHallsMap.put(exhibition_id, hall_id);
            }

        } catch (SQLException | DBException e) {
            throw new DBException("Cannot find all exhibition", e);
        }
        return exhibitions;
    }

    public void setHalls(long exhibition_id, String[] halls_id) throws DBException {
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

    public List<Hall> getHalls(long id) {
        List<Hall> hallListById = new CopyOnWriteArrayList<>();
        List<Hall> allHalls = new HallService().findAll();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_HALLS_BY_EXHIBITION_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long hall_id = rs.getLong(2);
                Hall hall = allHalls.stream()
                        .filter(i -> i.getId() == hall_id)
                        .findFirst().get();
                hallListById.add(hall);
            }
        } catch (SQLException | DBException e) {
            throw new DBException("Cannot find halls by exhibition id " + id, e);
        }
        return hallListById;
    }
}
