package com.exhibit.dao.impl;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.model.Exhibition;
import com.exhibit.model.services.ExhibitionService;
import com.exhibit.model.services.ServiceFactory;
import com.exhibit.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.util.constants.ExhibitionConstants.*;
import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;


public class ExhibitionDao implements ExhibitionService {
    static Mapper<Exhibition> mapper = MapperFactory.getInstance().getExhibitionMapper();
    Logger logger = LogManager.getLogger(INFO_LOGGER);

    public void add(final Exhibition exhibition) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(ADD_EXHIBITION_SQL);
            int i = 1;
            ps.setString(i++, exhibition.getTheme());
            ps.setString(i++, exhibition.getDetails());
            ps.setDate(i++, exhibition.getStartDate());
            ps.setDate(i++, exhibition.getEndDate());
            ps.setTime(i++, exhibition.getStartTime());
            ps.setTime(i++, exhibition.getEndTime());
            ps.setDouble(i, exhibition.getPrice());
            ps.executeUpdate();
            conn.commit();

            //set real id from db
            ExhibitionService service = ServiceFactory.getInstance().getExhibitionService();
            Optional<Exhibition> exhibitionInBase = service.findByTheme(exhibition.getTheme());
            if (exhibitionInBase.isPresent()) {
                exhibition.setId(exhibitionInBase.get().getId());
            } else {
                logger.error(EXHIBITION_INPUT_FAILED);
            }

        } catch (SQLException e) {
            ConnectionPool.rollbackConnection(conn, e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
    }

    public Optional<Exhibition> findById(final long id) {
        Optional<Exhibition> exhibition = Optional.empty();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(FIND_EXHIBITION_BY_ID);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                exhibition = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
        return exhibition;
    }


    public Optional<Exhibition> findByTheme(final String theme) {
        Optional<Exhibition> exhibition = Optional.empty();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(FIND_EXHIBITION_BY_THEME_SQL);
            ps.setString(1, theme);
            rs = ps.executeQuery();
            while (rs.next()) {
                exhibition = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
        return exhibition;
    }

    public List<Exhibition> findAll() {
        List<Exhibition> exhibitionList = new CopyOnWriteArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(FIND_ALL_EXHIBITIONS_SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                Exhibition exhibition = mapper.extractFromResultSet(rs);
                exhibitionList.add(exhibition);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
        return exhibitionList;
    }

    public int amountOfTickets(final long exhibitionId) {
        int amount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(FIND_AMOUNT_OF_TICKETS_BY_EXHIBITION_ID_SQL);
            ps.setLong(1, exhibitionId);
            rs = ps.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
        return amount;
    }

    public void cancel(final long exhibitionId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(UPDATE_EXHIBITION_PRICE_BY_ID_SQL);
            ps.setDouble(1, -1.0);
            ps.setLong(2, exhibitionId);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            ConnectionPool.rollbackConnection(conn, e);
        } finally {
            ConnectionPool.closeResources(conn, ps);
        }
    }

    public void delete(final long exhibitionId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(DELETE_EXHIBITION_BY_ID_SQL);
            ps.setLong(1, exhibitionId);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            ConnectionPool.rollbackConnection(conn, e);
        } finally {
            ConnectionPool.closeResources(conn, ps);
        }
    }
}
