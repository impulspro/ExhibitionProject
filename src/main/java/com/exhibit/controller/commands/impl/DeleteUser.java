package com.exhibit.controller.commands.impl;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandContainer;
import com.exhibit.dao.UserDao;
import com.exhibit.model.User;
import com.exhibit.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.exhibit.util.UtilConstants.*;

public class DeleteUser implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        UserService service = new UserDao();
        try {
            Optional<User> user = service.findByLogin(login);
            user.ifPresent(service::delete);
            logger.info("DeleteUser Command successfully");
            session.setAttribute(USER_MESSAGE, "you deleted user with login =  " + login);
        } catch (Exception e) {
            logger.info("DeleteUser Command failed");
            session.setAttribute(ERROR_MESSAGE, "cannot delete user");
        }

        Command command = CommandContainer.getCommand("listOfAllUsers_command");
        command.execute(req, resp);
    }
}
