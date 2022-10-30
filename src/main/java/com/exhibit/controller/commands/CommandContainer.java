package com.exhibit.controller.commands;

import com.exhibit.controller.commands.impl.admin.*;
import com.exhibit.controller.commands.impl.common.*;
import com.exhibit.controller.commands.impl.user.*;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("registrationCommand", new Registration());
        commands.put("logoutCommand", new LogOut());
        commands.put("loginCommand", new LogIn());
        commands.put("getHallsCommand", new GetHalls());
        commands.put("getExhibitionsCommand", new GetExhibitions());
        commands.put("buyTicketCommand", new BuyTicket());
        commands.put("addExhibitionCommand", new AddExhibition());
        commands.put("cancelExhibitionCommand", new CancelExhibition());
        commands.put("deleteExhibitionCommand", new DeleteExhibition());
        commands.put("searchUserCommand", new SearchUser());
        commands.put("listOfAllUsersCommand", new ListOfAllUsers());
        commands.put("deleteUserCommand", new DeleteUser());
        commands.put("addUserFundsCommand", new AddUserFunds());
        commands.put("returnTicketCommand", new ReturnTicket());
        commands.put("printPdfCommand", new PrintPdf());
    }

    private CommandContainer() {
    }

    public static Command getCommand(String command) {
        return commands.get(command);
    }

}
