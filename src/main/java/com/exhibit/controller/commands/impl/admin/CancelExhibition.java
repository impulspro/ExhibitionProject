package com.exhibit.controller.commands.impl.admin;

import com.exhibit.controller.commands.Command;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.exhibit.util.constants.UtilConstants.*;

public class CancelExhibition implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String exhibitionId = req.getParameter("exhibitionId");
        ExhibitionService service = ServiceFactory.getInstance().getExhibitionService();
        try {
            service.cancel(Long.parseLong(exhibitionId));
            logger.info("CancelExhibition Command successfully");
            session.setAttribute(USER_MESSAGE, "you canceled exhibition with id =  " + exhibitionId);
        } catch (Exception e) {
            logger.info("CancelExhibition Command failed");
            session.setAttribute(ERROR_MESSAGE, "cannot cancel exhibition");
        }
        String page = req.getHeader("Referer");
        try {
            resp.sendRedirect(page);
        } catch (IOException e) {
            logger.info("AddExhibition command redirect failed");
        }
    }
}
