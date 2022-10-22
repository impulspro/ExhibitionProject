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

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class BuyTicketCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("BuyTicket Command start ");

        long exhibition_id = Long.parseLong(req.getParameter("exhibition_id"));


        User user = (User) req.getSession().getAttribute("user");
        UserService service = new UserService();
        HttpSession session = req.getSession();

        String answer = service.buyTicket(user, exhibition_id);
        session.removeAttribute("user");
        session.removeAttribute("exhibition_id");
        session.setAttribute("user", user);

        if (answer != null && answer.equals("ok")) {
            logger.info("BuyTicket Command successful");
            session.setAttribute("user_message", "see you at the exhibition");
        } else {
            logger.info("GetExhibitions Command failed ");
            session.setAttribute("error_message", answer);
        }
        resp.sendRedirect("view/page/exhibitions.jsp");
    }
}
