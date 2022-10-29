package com.exhibit.controller.commands;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandR;
import com.exhibit.controller.commands.impl.admin.*;
import com.exhibit.controller.commands.impl.common.*;
import com.exhibit.controller.commands.impl.user.BuyTicket;
import com.exhibit.controller.commands.impl.user.PrintPdf;
import com.exhibit.controller.commands.impl.user.Registration;
import com.exhibit.controller.commands.implR.common.GetExhibitionsR;
import com.exhibit.controller.commands.implR.common.GetHallsR;
import com.exhibit.controller.commands.implR.common.LogInR;
import com.exhibit.controller.commands.implR.common.LogOutR;

import java.util.HashMap;
import java.util.Map;

public class CommandContainerR {
    private static final Map<String, CommandR> commands = new HashMap<>();

    static {

        commands.put("logoutCommand", new LogOutR());
        commands.put("loginCommand", new LogInR());
        commands.put("getHallsCommand", new GetHallsR());
        commands.put("getExhibitionsCommand", new GetExhibitionsR());

    }

    private CommandContainerR() {
    }

    public static CommandR getCommand(String command) {
        return commands.get(command);
    }

}
