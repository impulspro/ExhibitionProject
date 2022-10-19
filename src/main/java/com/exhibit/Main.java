package com.exhibit;

import com.exhibit.dao.PasswordHashing;
import com.exhibit.exeptions.DaoException;
import com.exhibit.model.Exhibition;
import com.exhibit.services.ExhibitionService;

public class Main {
    public static void main(String[] args) throws DaoException {
     /*   UserService service = new UserService();
        User user = service.findByLogin("user1").get();
     //   service.buyTicket(user,3);
        System.out.println(user.isTicketPresent(1));
        List<Ticket> tickets = service.getUserTickets(user);
        tickets.stream().forEach(s-> System.out.println(s));

*/
        String pass = "hello";

        System.out.println(PasswordHashing.toMD5("admin"));
        System.out.println(PasswordHashing.toMD5("password5"));
        System.out.println(PasswordHashing.toMD5("password6"));
        System.out.println(PasswordHashing.toMD5("password3"));
        System.out.println(PasswordHashing.toMD5("password4"));


        //ExhibitionService service = new ExhibitionService();
        //Exhibition exh = service.findById(8);
        //System.out.println(service.amountOfTickets(3));
       // exh.setTheme("NewTHHH");
      //  service.addExhibition(exh);
       // String [] halls = {"1","2"};
        //service.setHalls(exh.getId(), halls);
    }
}
