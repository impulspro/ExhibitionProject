package com.exhibit.dao.impl;

import com.exhibit.dao.ConnectionManager;
import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Hall;
import com.exhibit.services.HallService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.dao.constants.ExhibitionConstants.*;
import static com.exhibit.dao.constants.UtilConstants.*;

public class HallDao implements HallService{
    static Mapper<Hall> mapper = MapperFactory.getInstance().getHallMapper();
    Logger logger = LogManager.getLogger(INFO_LOGGER);

    private ConnectionManager manager;

    public HallDao(ConnectionManager manager) {
        this.manager = manager;
    }


    public static List<Date> getDaysBetweenDates(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, 1);
        Date endDatePlus = calendar.getTime();

        calendar.setTime(startDate);
        while (calendar.getTime().before(endDatePlus)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public List<Hall> findAll() {
        List<Hall> hallList = new CopyOnWriteArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(FIND_ALL_HALLS_SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                hallList.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            manager.closeResources(conn, ps, rs);
        }
        return hallList;
    }

    @Override
    public Optional<Hall> findById(final long hallId) {
        Optional<Hall> hall = Optional.empty();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(FIND_HALL_BY_ID_SQL);
            ps.setLong(1, hallId);
            rs = ps.executeQuery();
            if (rs.next()) {
                hall = Optional.ofNullable(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            manager.closeResources(conn, ps, rs);
        }
        return hall;
    }

    @Override
    public List<Exhibition> findAllExhibitionsByHall(long hallId) {
        List<Exhibition> exhibitions = new CopyOnWriteArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Mapper<Exhibition> exhibitionMapper = MapperFactory.getInstance().getExhibitionMapper();
        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(FIND_EXHIBITIONS_BY_HALL_ID_SQL);
            ps.setLong(1, hallId);
            rs = ps.executeQuery();
            while (rs.next()) {
                exhibitions.add(exhibitionMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            manager.closeResources(conn, ps, rs);
        }
        return exhibitions;
    }

    @Override
    public List<Hall> getHallsByExhibitionId(final long exhibitionId) {
        List<Hall> hallList = new CopyOnWriteArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = manager.getConnection();
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
            manager.closeResources(conn, ps, rs);
        }
        return hallList;
    }

    @Override
    public void setHallByExhibitionId(final long exhibitionId, final String[] hallsId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(SET_HALLS_SQL);
            ps.setLong(1, exhibitionId);
            for (String hall_id : hallsId) {
                ps.setLong(2, Long.parseLong(hall_id));
                ps.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            manager.rollbackConnection(conn, e);
        } finally {
            manager.closeResources(conn, ps);
        }
    }

    @Override
    public List<Date> getOccupiedDates(final long hallId) {
        List<Exhibition> exhibitionList = new CopyOnWriteArrayList<>();
        List<Date> dateList = new CopyOnWriteArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Mapper<Exhibition> exhibitionMapper = MapperFactory.getInstance().getExhibitionMapper();
        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(FIND_EXHIBITIONS_BY_HALL_SQL);
            ps.setLong(1, hallId);
            rs = ps.executeQuery();
            while (rs.next()) {
                exhibitionList.add(exhibitionMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            manager.closeResources(conn, ps, rs);
        }

        for (Exhibition exhibition : exhibitionList) {
            dateList.addAll(getDaysBetweenDates(exhibition.getStartDate(), exhibition.getEndDate()));
        }

        return dateList;
    }

    @Override
    public boolean isOccupiedOnDate(long hallId, Date date) {
        List<Date> dateList = getOccupiedDates(hallId);
        for (Date dateItem : dateList) {
            int result = dateItem.compareTo(date);
            if (result == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isOccupiedOnDate(long hallId, Date startDate, Date endDate) {
        List<Date> dateList = getOccupiedDates(hallId);
        List<Date> interval = getDaysBetweenDates(startDate, endDate);
        for (Date dateFromInterval : interval) {
            for (Date dateOccupied : dateList) {
                int result = dateOccupied.compareTo(dateFromInterval);
                if (result == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
