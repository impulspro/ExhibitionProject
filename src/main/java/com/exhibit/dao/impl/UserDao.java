package com.exhibit.dao.impl;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Ticket;
import com.exhibit.model.User;
import com.exhibit.model.services.ExhibitionService;
import com.exhibit.model.services.ServiceFactory;
import com.exhibit.model.services.UserService;
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

import static com.exhibit.util.constants.UserConstants.*;
import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;


public class UserDao implements UserService {
    static Mapper<User> mapper = MapperFactory.getInstance().getUserMapper();
    Logger logger = LogManager.getLogger(INFO_LOGGER);

    public Optional<User> findByLogin(final String login) {

        Optional<User> user = Optional.empty();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(FIND_USER_BY_LOGIN);
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
        return user;
    }

    public void add(final User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(ADD_USER_SQL);
            int i = 1;
            ps.setString(i++, user.getLogin());
            ps.setString(i++, user.getPassword());
            ps.setString(i++, AUTHORIZED_USER);
            ps.setDouble(i, USER_DEFAULT_MONEY);
            ps.executeUpdate();
            conn.commit();

            //set real id from db

            ps = conn.prepareStatement(FIND_REAL_USER_ID_BY_LOGIN_SQL);
            ps.setString(1, user.getLogin());
            rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id"));
            }

        } catch (SQLException e) {
            ConnectionPool.rollbackConnection(conn, e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
    }


    public List<User> findAll() {
        List<User> userList = new CopyOnWriteArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(FIND_ALL_USERS_SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = mapper.extractFromResultSet(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
        return userList;
    }

    public String buyTicket(final User user, final long exhibitionId) {
        ExhibitionService exhibitionService = ServiceFactory.getInstance().getExhibitionService();
        Optional<Exhibition> exhibition = exhibitionService.findById(exhibitionId);

        if (!exhibition.isPresent()) {
            return NO_EXHIBITION_FOUND;
        }

        List<Ticket> tickets = getUserTickets(user);
        if (!tickets.isEmpty()) {
            Optional<Ticket> ticket = tickets.stream().filter(t -> t.getExhibitionId() == exhibitionId).findFirst();
            if (ticket.isPresent()) {
                return ALREADY_BOUGHT_TICKET;
            }
        }

        if (exhibition.get().getPrice() > user.getMoney()) {
            return NO_ENOUGH_MONEY;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(ADD_USER_TICKET_SQL);

            ps.setLong(1, user.getId());
            ps.setLong(2, exhibitionId);
            ps.executeUpdate();

            ps = conn.prepareStatement(UPDATE_USER_MONEY_SQL);
            user.setMoney(user.getMoney() - exhibition.get().getPrice());

            ps.setDouble(1, user.getMoney());
            ps.setLong(2, user.getId());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            ConnectionPool.rollbackConnection(conn, e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }

        return BUY_TICKET_OK;
    }


    public void update(final User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(UPDATE_USER_SQL);
            int i = 1;
            ps.setString(i++, user.getLogin());
            ps.setString(i++, user.getPassword());
            ps.setString(i++, user.getRole());
            ps.setDouble(i++, user.getMoney());
            ps.setLong(i, user.getId());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            ConnectionPool.rollbackConnection(conn, e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
    }

    public List<Ticket> getUserTickets(final User user) {
        List<Ticket> tickets = new CopyOnWriteArrayList<>();
        Mapper<Ticket> ticketMapper = MapperFactory.getInstance().getTicketMapper();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(FIND_USER_TICKETS_SQL);
            ps.setLong(1, user.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                tickets.add(ticketMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            ConnectionPool.closeResources(conn, ps, rs);
        }
        return tickets;
    }

    public boolean isTicketPreset(final String login, final long exhibitionId) {
        Optional<User> user = findByLogin(login);
        if (user.isPresent()) {
            List<Ticket> tickets = getUserTickets(user.get());
            Optional<Ticket> ticket = tickets.stream().filter(t -> t.getExhibitionId() == exhibitionId).findFirst();
            return ticket.isPresent();
        } else {
            return false;
        }
    }

    public void delete(final User user) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(DELETE_USER_BY_ID_SQL);
            ps.setLong(1, user.getId());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            ConnectionPool.rollbackConnection(conn, e);
        } finally {
            ConnectionPool.closeResources(conn, ps);
        }
    }
}
