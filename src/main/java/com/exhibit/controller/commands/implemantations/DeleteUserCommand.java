package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandContainer;
import com.exhibit.model.User;
import com.exhibit.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.exhibit.util.UtilConstants.INFO_LOGGER;

public class DeleteUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        UserService service = new UserService();
        try {
            Optional<User> user = service.findByLogin(login);
            user.ifPresent(service::delete);
            logger.info("DeleteUser Command successfully");
            session.setAttribute("user_message", "you deleted user with login =  " + login);
        } catch (Exception e) {
            logger.info("DeleteUser Command failed");
            session.setAttribute("error_message", "cannot delete user");
        }

        Command command = CommandContainer.getCommand("listOfAllUsers_command");
        command.execute(req, resp);
    }
}
