package com.exhibit.controller.commands;

import com.exhibit.controller.commands.implemantations.*;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("registration_command", new RegistrationCommand());
        commands.put("logout_command", new LogoutCommand());
        commands.put("login_command", new LoginCommand());
        commands.put("home_command", new HomeCommand());
        commands.put("getHalls_command", new GetHallsCommand());
        commands.put("getExhibitions_command", new GetExhibitionsCommand());
        commands.put("buyTicket_command", new BuyTicketCommand());
        commands.put("addExhibition_command", new AddExhibitionCommand());
        commands.put("cancelExhibition_command", new CancelExhibitionCommand());
        commands.put("deleteExhibition_command", new DeleteExhibitionCommand());
        commands.put("searchUser_command", new SearchUserCommand());
        commands.put("listOfAllUsers_command", new ListOfAllUsersCommand());
        commands.put("deleteUser_command", new DeleteUserCommand());
        commands.put("printPdf_command", new PrintPdfCommand());
    }

    private CommandContainer() {
    }

    public static Command getCommand(String command) {
        return commands.get(command);
    }

}
