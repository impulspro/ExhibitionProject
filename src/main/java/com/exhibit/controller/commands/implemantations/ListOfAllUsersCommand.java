package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.model.User;
import com.exhibit.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.exhibit.util.UtilConstants.INFO_LOGGER;

public class ListOfAllUsersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String redirectPage = "view/page/adminPanel.jsp";
        try {
            UserService userService = new UserService();
            List<User> userList = userService.findAll();
            logger.info("ListOfAllUsers command execute successful");
            req.getSession().setAttribute("userList", userList);
        } catch (Exception e) {
            logger.info("ListOfAllUsers command execute failed");
            req.getSession().setAttribute("error_message", "List of users error");
        }
        resp.sendRedirect(redirectPage);
    }
}
