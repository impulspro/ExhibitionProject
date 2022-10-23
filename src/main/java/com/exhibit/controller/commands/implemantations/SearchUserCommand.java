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
import java.util.Optional;

import static com.exhibit.util.UtilConstants.INFO_LOGGER;

public class SearchUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        HttpSession session = req.getSession();
        String redirectPage = "view/page/adminPanel.jsp";
        try {
            UserService userService = new UserService();
            Optional<User> user = userService.findByLogin(login);
            if (user.isPresent()) {
                logger.info("SearchUser command execute successful = " + login);
                req.getSession().setAttribute("search_user", user.get());
            } else {
                session.setAttribute("error_message", "no such user in base");
            }

        } catch (Exception e) {
            logger.info("SearchUser command execute failed for login = " + login);
            req.getSession().setAttribute("error_message", "SearchUser error");
        }
        resp.sendRedirect(redirectPage);
    }
}
