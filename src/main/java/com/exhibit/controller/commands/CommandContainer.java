package com.exhibit.controller.commands;

import com.exhibit.controller.commands.implemantations.*;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Map<String, Command> commands = new HashMap<>();

    private CommandContainer() {
    }

    static {
        commands.put("registration_command", new RegistrationCommand());
        commands.put("logout_command", new LogoutCommand());
        commands.put("login_command", new LoginCommand());
        commands.put("home_command", new HomeCommand());
        commands.put("getHalls_command", new GetHallsCommand());
        /*commands.put("error", new ExceptionCommand());
        commands.put("addExhibition", new AddExhibitionCommand());
        commands.put("buy", new BuyTicketCommand());
        commands.put("statistics", new StatisticsCommand());
        commands.put("cancel", new CancelExhibitionCommand());
        commands.put("plan", new PlanExhibitionCommand());
        commands.put("filter", new FilterByDateCommand());
*/
    }

    public static Command getCommand(String command) {
        return commands.get(command);
    }

}
