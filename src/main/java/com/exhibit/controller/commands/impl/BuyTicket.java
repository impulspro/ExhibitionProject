package com.exhibit.controller.commands.impl;

import com.exhibit.controller.commands.Command;
import com.exhibit.dao.UserDao;
import com.exhibit.model.User;
import com.exhibit.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.exhibit.util.UtilConstants.*;

public class BuyTicket implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp){
        logger.info("BuyTicket Command start ");

        long exhibitionId = Long.parseLong(req.getParameter("exhibition_id"));


        User user = (User) req.getSession().getAttribute("user");
        UserService service = new UserDao();
        HttpSession session = req.getSession();

        String answer = service.buyTicket(user, exhibitionId);
        session.removeAttribute("user");
        session.removeAttribute("exhibition_id");
        session.setAttribute("user", user);

        if (answer != null && answer.equals("ok")) {
            logger.info("BuyTicket Command successful");
            session.setAttribute(USER_MESSAGE, "see you at the exhibition");
        } else {
            logger.info("GetExhibitions Command failed ");
            session.setAttribute(ERROR_MESSAGE, answer);
        }
        try {
            resp.sendRedirect("view/page/exhibitions.jsp");
        } catch (IOException e) {
            logger.info("GetExhibitions Command redirect failed ");
        }
    }
}
