package com.exhibit.dao.impl;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.dao.model.Hall;
import com.exhibit.services.HallService;
import com.exhibit.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.util.constants.ExhibitionConstants.*;
import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class HallDao implements HallService {
    static Mapper<Hall> mapper = MapperFactory.getInstance().getHallMapper();
    Logger logger = LogManager.getLogger(INFO_LOGGER);

    public List<Hall> findAll() {
        List<Hall> hallList = new CopyOnWriteArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(FIND_ALL_HALLS_SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                hallList.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
        return hallList;
    }

    @Override
    public List<Hall> getHallByExhibitionId(final long exhibitionId) {
        List<Hall> hallList = new CopyOnWriteArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(FIND_HALLS_BY_EXHIBITION_ID);
            ps.setLong(1, exhibitionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Hall hall = mapper.extractFromResultSet(rs);
                hallList.add(hall);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
        return hallList;
    }

    @Override
    public void setHallByExhibitionId(final long exhibitionId, final String[] hallsId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(SET_HALLS_SQL);
            ps.setLong(1, exhibitionId);
            for (String hall_id : hallsId) {
                ps.setLong(2, Long.parseLong(hall_id));
                ps.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            ConnectionPool.rollbackConnection(conn, e);
        } finally {
            ConnectionPool.closeResources(conn, ps);
        }
    }

}
