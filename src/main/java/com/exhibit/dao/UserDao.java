package com.exhibit.dao;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.exeptions.DBException;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Ticket;
import com.exhibit.model.User;
import com.exhibit.services.ExhibitionService;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.util.constants.ExhibitionConstants.FIND_ALL_EXHIBITIONS_SQL;
import static com.exhibit.util.constants.ExhibitionConstants.FIND_EXHIBITIONS_RELATED_HALLS_SQL;
import static com.exhibit.util.constants.UserConstants.*;


public class UserDao {
    static Mapper mapper = MapperFactory.getInstance().getUserMapper();

    public static User findByLogin(String login) throws DBException {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_USER_BY_LOGIN)){
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return (User) mapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Cannot find user", e);
        }
        return null;
    }

    public static void add(User user) throws DBException {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(ADD_USER_SQL)) {
            int i = 1;
            ps.setString(i++, user.getLogin());
            ps.setString(i++, user.getPassword());
            ps.setString(i++, AUTHORIZED_USER);
            ps.executeUpdate();
            System.out.println(user);
            System.out.println("-avasd----------");
            try (PreparedStatement ps2 = conn.prepareStatement(FIND_REAL_USER_ID_BY_LOGIN_SQL)){
                ps2.setString(1, user.getLogin());
                ResultSet rs = ps2.executeQuery();
                if (rs.next()) {
                    long real_id = rs.getLong("id");
                    user.setId(real_id);
                }
                rs.close();
            }
        } catch (SQLException e) {
            throw new DBException("Invalid user input", e);
        }

    }

    public List<User> findAll() {
        List<User> userList = new CopyOnWriteArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_USERS_SQL)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long asdf = rs.getLong(1);
                User user = (User) mapper.extractFromResultSet(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DBException("Cannot find all users", e);
        }
        return userList;
    }

    public String buyTicket(User user, long exhibition_id) {
        Exhibition exhibition = new ExhibitionService().findById(exhibition_id);
        List<Ticket> tickets = getUserTickets(user);
        if (tickets != null && !tickets.isEmpty()){
            Optional<Ticket> ticket = tickets.stream().filter(t -> t.getExhibition_id() == exhibition_id).findFirst();
            if (!ticket.isPresent()) {
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
            ps.setLong(i++, exhibition_id);
            ps.executeUpdate();
            user.setMoney(user.getMoney() - exhibition.getPrice());
            update(user);
        } catch (SQLException e) {
            throw new DBException("Invalid user input", e);
        }

        return "ok";
    }

    public void update(User user){
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_USER_SQL)) {
            int i = 1;
            ps.setString(i++, user.getLogin());
            ps.setString(i++, user.getPassword());
            ps.setString(i++, user.getRole());
            ps.setDouble(i++, user.getMoney());
            ps.setLong(i++,user.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Cannot update user", e);
        }
    }

    public List<Ticket> getUserTickets(User user) {
        List<Ticket> tickets = new CopyOnWriteArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_USER_TICKETS_SQL)) {
            ps.setLong(1, user.getId());
            ResultSet rs = ps.executeQuery();
            Mapper ticketMapper = MapperFactory.getInstance().getTicketMapper();
            while (rs.next()) {
                tickets.add((Ticket) ticketMapper.extractFromResultSet(rs));
            }

        } catch (SQLException | DBException e) {
            throw new DBException("Cannot find all user tickets", e);
        }
        return tickets;
    }
    public boolean isTicketPreset(String login, long exhibition_id){

        User user = findByLogin(login);
        List<Ticket> tickets = getUserTickets(user);

        Optional<Ticket> ticket = tickets.stream().filter(t -> t.getExhibition_id() == exhibition_id).findFirst();

        if (ticket.isPresent()) {
            return true;
        } else {
            return false;
        }


    }
}
