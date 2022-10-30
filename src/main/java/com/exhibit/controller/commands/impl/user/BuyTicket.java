package com.exhibit.controller.commands.impl.user;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.model.User;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import com.exhibit.util.constants.DispatchType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.exhibit.util.constants.UtilConstants.*;

public class BuyTicket implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp) {
        logger.info("BuyTicket Command start ");

        long exhibitionId = Long.parseLong(req.getParameter("exhibitionId"));


        User user = (User) req.getSession().getAttribute("user");
        UserService service = ServiceFactory.getInstance().getUserService();
        HttpSession session = req.getSession();

        String answer = service.buyTicket(user, exhibitionId);
        session.removeAttribute("user");
        session.removeAttribute("exhibitionId");
        session.setAttribute("user", user);

        if (answer != null && answer.equals("ok")) {
            session.setAttribute(USER_MESSAGE, "see you at the exhibition");
        } else {
            logger.info(answer);
            session.setAttribute(ERROR_MESSAGE, answer);
        }

        return new CommandResponse(DispatchType.REDIRECT, EXHIBITIONS_JSP);
    }
}
