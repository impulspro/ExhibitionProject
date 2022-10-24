package com.exhibit.dao;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.exeptions.DaoException;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Hall;
import com.exhibit.services.ExhibitionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.util.ExhibitionConstants.*;


public class ExhibitionDao implements ExhibitionService {
    static Mapper<Exhibition> mapper = MapperFactory.getInstance().getExhibitionMapper();

    public void add(Exhibition exhibition) {
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
            ExhibitionService service = new ExhibitionDao();
            Optional<Exhibition> exhibitionInBase = service.findByTheme(exhibition.getTheme());
            if (exhibitionInBase.isPresent()) {
                exhibition.setId(exhibitionInBase.get().getId());
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
        Optional<Exhibition> exhibition = Optional.empty();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(FIND_EXHIBITION_BY_THEME_SQL)) {
            prepSt.setString(1, theme);
            ResultSet rs = prepSt.executeQuery();
            while (rs.next()) {
                exhibition = Optional.of(mapper.extractFromResultSet(rs));
            }
            return exhibition;
        } catch (SQLException e) {
            throw new DaoException("Cannot find exhibition by theme " + theme, e);
        }
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

    public void setHalls(long exhibitionId, String[] hallsId) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SET_HALLS_SQL)) {
            ps.setLong(1, exhibitionId);
            for (String hall_id : hallsId) {
                ps.setLong(2, Long.parseLong(hall_id));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot set halls for exhibition " + exhibitionId, e);
        }
    }

    public List<Hall> getHalls(long id) {
        List<Hall> hallListById = new CopyOnWriteArrayList<>();
        List<Hall> allHalls = new HallDao().findAll();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_HALLS_BY_EXHIBITION_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long hallId = rs.getLong(2);
                Optional<Hall> hall = allHalls.stream()
                        .filter(i -> i.getId() == hallId)
                        .findFirst();
                hall.ifPresent(hallListById::add);
            }
        } catch (Exception e) {
            throw new DaoException("Cannot find halls by exhibition id " + id, e);
        }
        return hallListById;
    }

    public int amountOfTickets(long exhibitionId) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_AMOUNT_OF_TICKETS_BY_EXHIBITION_ID_SQL)) {
            ps.setLong(1, exhibitionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find amount of tickets " + exhibitionId, e);
        }
    }

    public void cancel(long exhibitionId) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_EXHIBITION_PRICE_BY_ID_SQL)) {
            ps.setDouble(1, -1.0);
            ps.setLong(2, exhibitionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Cannot update ticket price " + exhibitionId, e);
        }
    }

    public void delete(long exhibitionId) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(DELETE_EXHIBITION_BY_ID_SQL)) {
            prepSt.setLong(1, exhibitionId);
            prepSt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete exhibition " + exhibitionId, e);
        }
    }
}
