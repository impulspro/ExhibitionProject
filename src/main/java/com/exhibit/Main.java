package com.exhibit;

import com.exhibit.dao.DaoException;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Ticket;
import com.exhibit.model.User;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.UserService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws DaoException {
     /*   UserService service = new UserService();
        User user = service.findByLogin("user1").get();
     //   service.buyTicket(user,3);
        System.out.println(user.isTicketPresent(1));
        List<Ticket> tickets = service.getUserTickets(user);
        tickets.stream().forEach(s-> System.out.println(s));

*/
        ExhibitionService service = new ExhibitionService();
        Exhibition exh = service.findById(8);
        System.out.println(service.amountOfTickets(3));
       // exh.setTheme("NewTHHH");
      //  service.addExhibition(exh);
       // String [] halls = {"1","2"};
        //service.setHalls(exh.getId(), halls);
    }
}
