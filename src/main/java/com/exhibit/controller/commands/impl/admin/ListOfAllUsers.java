package com.exhibit.controller.commands.impl.admin;

import com.exhibit.controller.commands.Command;
import com.exhibit.dao.model.User;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.exhibit.util.constants.UtilConstants.ERROR_MESSAGE;
import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class ListOfAllUsers implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {

        String redirectPage = "view/page/adminPanel.jsp";
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            List<User> userList = userService.findAll();
            logger.info("ListOfAllUsers command execute successful");
            req.getSession().setAttribute("userList", userList);
        } catch (Exception e) {
            logger.info("ListOfAllUsers command execute failed");
            req.getSession().setAttribute(ERROR_MESSAGE, "List of users error");
        }
        try {
            resp.sendRedirect(redirectPage);
        } catch (IOException e) {
            logger.info("ListOfAllUsers redirect failed");
        }
    }
}
