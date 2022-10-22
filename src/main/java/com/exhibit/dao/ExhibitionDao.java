package com.exhibit.dao;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.exeptions.DaoException;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Hall;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.HallService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.util.ExhibitionConstants.*;


public class ExhibitionDao {
    static Mapper<Exhibition> mapper = MapperFactory.getInstance().getExhibitionMapper();

    public static void add(Exhibition exhibition) throws DaoException {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(ADD_EXHIBITION_SQL)) {
            int i = 1;
            prepSt.setString(i++, exhibition.getTheme());
            prepSt.setString(i++, exhibition.getDetails());
            prepSt.setDate(i++, exhibition.getStartDate());
            prepSt.setDate(i++, exhibition.getEndDate());
            prepSt.setTime(i++, exhibition.getStartTime());
            prepSt.setTime(i++, exhibition.getEndTime());
            prepSt.setDouble(i, exhibition.getPrice());
            prepSt.executeUpdate();
            ExhibitionService service = new ExhibitionService();
            if (service.findByTheme(exhibition.getTheme()).isPresent()) {
                exhibition.setId(service.findByTheme(exhibition.getTheme()).get().getId());
            } else {
                throw new DaoException("Invalid exhibition input");
            }
        } catch (SQLException e) {
            throw new DaoException("Invalid exhibition input", e);
        }
    }

    public Optional<Exhibition> findById(long id) throws DaoException {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(FIND_EXHIBITION_BY_THEME_ID)) {
            prepSt.setLong(1, id);
            ResultSet rs = prepSt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException | DaoException e) {
            throw new DaoException("Cannot find exhibition by id " + id, e);
        }
        return Optional.empty();
    }

    public Optional<Exhibition> findByTheme(String theme) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(FIND_EXHIBITION_BY_THEME_SQL)) {
            prepSt.setString(1, theme);
            ResultSet rs = prepSt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find exhibition by theme " + theme, e);
        }
        return Optional.empty();
    }

    public List<Exhibition> findAll() {
        List<Exhibition> exhibitions = new CopyOnWriteArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_EXHIBITIONS_SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                exhibitions.add(mapper.extractFromResultSet(rs));
            }

        } catch (SQLException e) {
            throw new DaoException("Cannot find all exhibition", e);
        }
        return exhibitions;
    }

    public void setHalls(long exhibition_id, String[] halls_id) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SET_HALLS_SQL)) {
            ps.setLong(1, exhibition_id);
            for (String hall_id : halls_id) {
                ps.setLong(2, Long.parseLong(hall_id));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot set halls for exhibition " + exhibition_id, e);
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
                Optional<Hall> hall = allHalls.stream()
                        .filter(i -> i.getId() == hall_id)
                        .findFirst();
                hall.ifPresent(hallListById::add);
            }
        } catch (Exception e) {
            throw new DaoException("Cannot find halls by exhibition id " + id, e);
        }
        return hallListById;
    }

    public int amountOfTickets(long exhibition_id) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_AMOUNT_OF_TICKETS_BY_EXHIBITION_ID_SQL)) {
            ps.setLong(1, exhibition_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find amount of tickets " + exhibition_id, e);
        }
    }

    public void cancelExhibition(long exhibition_id) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_EXHIBITION_PRICE_BY_ID_SQL)) {
            ps.setDouble(1, -1.0);
            ps.setLong(2, exhibition_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Cannot update ticket price " + exhibition_id, e);
        }
    }

    public void deleteExhibition(long exhibition_id) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(DELETE_EXHIBITION_BY_ID_SQL)) {
            prepSt.setLong(1, exhibition_id);
            prepSt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete exhibition " + exhibition_id, e);
        }
    }
}
