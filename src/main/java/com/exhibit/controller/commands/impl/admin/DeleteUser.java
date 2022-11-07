package com.exhibit.controller.commands.impl.admin;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandContainer;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.connection.ConnectionManager;
import com.exhibit.dao.model.User;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import com.exhibit.dao.constants.DispatchType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.exhibit.dao.constants.UtilConstants.*;

public class DeleteUser implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp, final ConnectionManager manager) {
        UserService userService = ServiceFactory.getInstance().getUserService(manager);
        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        try {
            Optional<User> user = userService.findByLogin(login);
            user.ifPresent(value -> userService.delete(value.getId()));
            session.setAttribute(USER_MESSAGE, "you deleted user with login =  " + login);
            req.getSession().removeAttribute("searchUser");
        } catch (Exception e) {
            logger.info(e);
            session.setAttribute(ERROR_MESSAGE, "cannot delete user");
        }

        CommandResponse cr =  CommandContainer.getCommand("listOfAllUsersCommand").execute(req, resp, manager);
        cr.setDispatchType(DispatchType.REDIRECT);
        return cr;
    }
}
