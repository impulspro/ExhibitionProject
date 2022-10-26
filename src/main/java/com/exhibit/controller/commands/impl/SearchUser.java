package com.exhibit.controller.commands.impl;

import com.exhibit.controller.commands.Command;
import com.exhibit.model.User;
import com.exhibit.model.services.ServiceFactory;
import com.exhibit.model.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.exhibit.util.constants.UtilConstants.ERROR_MESSAGE;
import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class SearchUser implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {
        String login = req.getParameter("login");
        HttpSession session = req.getSession();
        String redirectPage = "view/page/adminPanel.jsp";
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            Optional<User> user = userService.findByLogin(login);
            if (user.isPresent()) {
                String info = "SearchUser command execute successful = " + login;
                logger.info(info);
                req.getSession().setAttribute("searchUser", user.get());
            } else {
                session.setAttribute(ERROR_MESSAGE, "no such user in base");
            }

        } catch (Exception e) {
            String info = "SearchUser command execute failed for login = " + login;
            logger.info(info);
            req.getSession().setAttribute(ERROR_MESSAGE, "SearchUser error");
        }
        try {
            resp.sendRedirect(redirectPage);
        } catch (IOException e) {
            logger.info("SearchUser redirect failed");
        }
    }
}
