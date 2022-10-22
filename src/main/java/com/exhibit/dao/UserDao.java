package com.exhibit.dao;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.exeptions.DaoException;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Ticket;
import com.exhibit.model.User;
import com.exhibit.services.ExhibitionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.util.UserConstants.*;


public class UserDao {
    static Mapper<User> mapper = MapperFactory.getInstance().getUserMapper();

    public Optional<User> findByLogin(String login) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_USER_BY_LOGIN)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find user", e);
        }
        return Optional.empty();
    }

    public void add(User user) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(ADD_USER_SQL)) {
            int i = 1;
            ps.setString(i++, user.getLogin());
            ps.setString(i++, user.getPassword());
            ps.setString(i++, AUTHORIZED_USER);
            ps.setDouble(i, USER_DEFAULT_MONEY);
            ps.executeUpdate();
            try (PreparedStatement ps2 = conn.prepareStatement(FIND_REAL_USER_ID_BY_LOGIN_SQL)) {
                ps2.setString(1, user.getLogin());
                ResultSet rs = ps2.executeQuery();
                if (rs.next()) {
                    long real_id = rs.getLong("id");
                    user.setId(real_id);
                }
                rs.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Invalid user input", e);
        }

    }

    public List<User> findAll() {
        List<User> userList = new CopyOnWriteArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_USERS_SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = mapper.extractFromResultSet(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find all users", e);
        }
        return userList;
    }

    public String buyTicket(User user, long exhibition_id) {
        ExhibitionService exhibitionService = new ExhibitionService();
        Exhibition exhibition;
        if (exhibitionService.findById(exhibition_id).isPresent()) {
            exhibition = exhibitionService.findById(exhibition_id).get();
        } else {
            return "No such exhibition found";
        }

        List<Ticket> tickets = getUserTickets(user);
        if (tickets != null && !tickets.isEmpty()) {
            Optional<Ticket> ticket = tickets.stream().filter(t -> t.getExhibition_id() == exhibition_id).findFirst();
            if (ticket.isPresent()) {
                return "You already buy a ticket to this exhibition";
            }
        }

        if (exhibition.getPrice() > user.getMoney()) {
            return "Not enough money on your account";
        }
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(ADD_USER_TICKET_SQL)) {
            int i = 1;
            ps.setLong(i++, user.getId());
            ps.setLong(i, exhibition_id);
            ps.executeUpdate();
            user.setMoney(user.getMoney() - exhibition.getPrice());
            update(user);
        } catch (SQLException e) {
            throw new DaoException("Invalid user input", e);
        }

        return "ok";
    }

    public void update(User user) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_USER_SQL)) {
            int i = 1;
            ps.setString(i++, user.getLogin());
            ps.setString(i++, user.getPassword());
            ps.setString(i++, user.getRole());
            ps.setDouble(i++, user.getMoney());
            ps.setLong(i, user.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Cannot update user", e);
        }
    }

    public List<Ticket> getUserTickets(User user) {
        List<Ticket> tickets = new CopyOnWriteArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_USER_TICKETS_SQL)) {
            ps.setLong(1, user.getId());
            ResultSet rs = ps.executeQuery();
            Mapper<Ticket> ticketMapper = MapperFactory.getInstance().getTicketMapper();
            while (rs.next()) {
                tickets.add(ticketMapper.extractFromResultSet(rs));
            }

        } catch (SQLException e) {
            throw new DaoException("Cannot find all user tickets", e);
        }
        return tickets;
    }

    public boolean isTicketPreset(String login, long exhibition_id) {

        Optional<User> user = findByLogin(login);

        if (user.isPresent()) {
            List<Ticket> tickets = getUserTickets(user.get());
            Optional<Ticket> ticket = tickets.stream().filter(t -> t.getExhibition_id() == exhibition_id).findFirst();
            return ticket.isPresent();
        } else {
            return false;
        }

    }
}
