package com.exhibit.controller.commands;

import com.exhibit.controller.commands.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("registration_command", new Registration());
        commands.put("logout_command", new LogOut());
        commands.put("login_command", new LogIn());
        commands.put("home_command", new Home());
        commands.put("getHalls_command", new GetHalls());
        commands.put("getExhibitions_command", new GetExhibitions());
        commands.put("buyTicket_command", new BuyTicket());
        commands.put("addExhibition_command", new AddExhibition());
        commands.put("cancelExhibition_command", new CancelExhibition());
        commands.put("deleteExhibition_command", new DeleteExhibition());
        commands.put("searchUser_command", new SearchUser());
        commands.put("listOfAllUsers_command", new ListOfAllUsers());
        commands.put("deleteUser_command", new DeleteUser());
        commands.put("printPdf_command", new PrintPdf());
    }

    private CommandContainer() {
    }

    public static Command getCommand(String command) {
        return commands.get(command);
    }

}
