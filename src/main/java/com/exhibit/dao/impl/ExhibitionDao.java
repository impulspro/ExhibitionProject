package com.exhibit.dao.impl;

import com.exhibit.dao.ConnectionManager;
import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.services.ExhibitionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.dao.constants.ExhibitionConstants.*;
import static com.exhibit.dao.constants.UtilConstants.*;


public class ExhibitionDao implements ExhibitionService {
    static Mapper<Exhibition> mapper = MapperFactory.getInstance().getExhibitionMapper();
    Logger logger = LogManager.getLogger(INFO_LOGGER);
    private ConnectionManager manager;

    public ExhibitionDao(ConnectionManager manager) {
        this.manager = manager;
    }

    public void add(final Exhibition exhibition) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = manager.getConnection();
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
            Optional<Exhibition> exhibitionInBase = findByTheme(exhibition.getTheme());
            if (exhibitionInBase.isPresent()) {
                exhibition.setId(exhibitionInBase.get().getId());
            } else {
                logger.error(EXHIBITION_INPUT_FAILED);
            }
        } catch (SQLException e) {
            manager.rollbackConnection(conn, e);
        } finally {
            manager.closeResources(conn, ps);
        }
    }

    @Override
    public boolean inPast(final long exhibitionId){
        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
        Optional<Exhibition> exhibition = findById(exhibitionId);
        if (exhibition.isPresent()){
            if (exhibition.get().getEndDate().compareTo(date) < 0){
                return true;
            }
        } else {
            logger.error("Cannot find exhibition");
        }
        return false;
    }

    public boolean isTicketCanBeReturnByExhibition(final long exhibitionId){
        Optional<Exhibition> exhibition = findById(exhibitionId);

        if (exhibition.isPresent()){
            java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
            Date endDate = exhibition.get().getEndDate();
            return endDate.compareTo(date) >= 0;
        } else {
            logger.error("Cannot find the exhibition");
        }
        return false;
    }
    public Optional<Exhibition> findById(final long id) {
        Optional<Exhibition> exhibition = Optional.empty();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(FIND_EXHIBITION_BY_ID);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                exhibition = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            manager.closeResources(conn, ps, rs);
        }
        return exhibition;
    }


    public Optional<Exhibition> findByTheme(final String theme) {
        Optional<Exhibition> exhibition = Optional.empty();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(FIND_EXHIBITION_BY_THEME_SQL);
            ps.setString(1, theme);
            rs = ps.executeQuery();
            while (rs.next()) {
                exhibition = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            manager.closeResources(conn, ps, rs);
        }
        return exhibition;
    }

    public List<Exhibition> findAll() {
        List<Exhibition> exhibitionList = new CopyOnWriteArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(FIND_ALL_EXHIBITIONS_SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                Exhibition exhibition = mapper.extractFromResultSet(rs);
                exhibitionList.add(exhibition);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            manager.closeResources(conn, ps, rs);
        }
        return exhibitionList;
    }

    @Override
    public int amountOfExhibitions(final String sortType, final String sortParam) {
        int amount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = manager.getConnection();
            if (sortParam != null && !sortParam.isEmpty()) {
                if (sortType.equals(SORT_BY_HALL)) {
                    ps = conn.prepareStatement(FIND_EXHIBITIONS_AMOUNT_WHERE_HALL_SQL);
                    ps.setLong(1, Long.parseLong(sortParam));
                } else {
                    ps = conn.prepareStatement(FIND_EXHIBITIONS_AMOUNT_WHERE_DATE_SQL);
                    ps.setDate(1, Date.valueOf(sortParam));
                }
            } else {
                ps = conn.prepareStatement(FIND_EXHIBITIONS_AMOUNT_SQL);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (SQLException | IllegalArgumentException e) {
            amount = amountOfExhibitions();
            logger.error(e);
        } finally {
            manager.closeResources(conn, ps, rs);
        }
        return amount;
    }

    @Override
    public int amountOfExhibitions() {
        int amount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(FIND_EXHIBITIONS_AMOUNT_SQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            manager.closeResources(conn, ps, rs);
        }
        return amount;
    }

    @Override
    public List<Exhibition> findSortByWhereIs(final String sortType, final String sortParam, final int pageNum) {
        List<Exhibition> exhibitionList = new CopyOnWriteArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = manager.getConnection();
            int i = 1;
            if (sortParam == null || sortParam.isEmpty()) {
                switch (sortType) {
                    case SORT_BY_THEME:
                        ps = conn.prepareStatement(SORT_BY_THEME_SQL);
                        break;
                    case SORT_BY_PRICE:
                        ps = conn.prepareStatement(SORT_BY_PRICE_SQL);
                        break;
                    case SORT_BY_DATE:
                        ps = conn.prepareStatement(SORT_BY_DATE_SQL);
                        break;
                    default:
                        logger.error("Cannot get a sortType");
                }
            } else {
                switch (sortType) {
                    case SORT_BY_HALL:
                        ps = conn.prepareStatement(SORT_BY_HALL_WHERE_SQL);
                        ps.setLong(i++, Long.parseLong(sortParam));
                        break;
                    case SORT_BY_DATE:
                        ps = conn.prepareStatement(SORT_BY_DATE_WHERE_SQL);
                        ps.setDate(i++, Date.valueOf(sortParam));
                        break;
                    default:
                        logger.error("Cannot get a sortParam");
                }
            }

            if (ps != null) {
                ps.setInt(i++, RECORDS_PER_PAGE);
                ps.setInt(i, (pageNum - 1) * RECORDS_PER_PAGE);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Exhibition exhibition = mapper.extractFromResultSet(rs);
                    exhibitionList.add(exhibition);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            manager.closeResources(conn, ps, rs);
        }
        return exhibitionList;
    }


    @Override

    public void cancel(final long exhibitionId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(UPDATE_EXHIBITION_PRICE_BY_ID_SQL);
            ps.setDouble(1, -1.0);
            ps.setLong(2, exhibitionId);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            manager.rollbackConnection(conn, e);
        } finally {
            manager.closeResources(conn, ps);
        }
    }

    public void delete(final long exhibitionId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(DELETE_EXHIBITION_BY_ID_SQL);
            ps.setLong(1, exhibitionId);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            manager.rollbackConnection(conn, e);
        } finally {
            manager.closeResources(conn, ps);
        }
    }

    public int amountOfTicketsByExhibition(final long exhibitionId) {
        int amount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = manager.getConnection();
            ps = conn.prepareStatement(FIND_AMOUNT_OF_TICKETS_BY_EXHIBITION_ID_SQL);
            ps.setLong(1, exhibitionId);
            rs = ps.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            manager.closeResources(conn, ps, rs);
        }
        return amount;
    }
}



