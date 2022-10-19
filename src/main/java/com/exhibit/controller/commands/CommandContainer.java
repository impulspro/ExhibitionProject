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
       /* commands.put("error", new ExceptionCommand());
        commands.put("addExhibition", new AddExhibitionCommand());
        commands.put("buy", new BuyTicketCommand());
        commands.put("statistics", new StatisticsCommand());
        commands.put("cancel", new CancelExhibitionCommand());
        commands.put("plan", new PlanExhibitionCommand());
        commands.put("filter", new FilterByDateCommand());
*/
    }

    private CommandContainer() {
    }

    public static Command getCommand(String command) {
        return commands.get(command);
    }

}
