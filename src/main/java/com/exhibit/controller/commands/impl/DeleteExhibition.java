package com.exhibit.controller.commands.impl;

import com.exhibit.controller.commands.Command;
import com.exhibit.model.services.ExhibitionService;
import com.exhibit.model.services.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.exhibit.util.constants.UtilConstants.*;

public class DeleteExhibition implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String exhibitionId = req.getParameter("exhibitionId");
        ExhibitionService service = ServiceFactory.getInstance().getExhibitionService();
        try {
            service.delete(Long.parseLong(exhibitionId));
            logger.info("DeleteExhibition Command successfully");
            session.setAttribute(USER_MESSAGE, "you deleted exhibition with id =  " + exhibitionId);
        } catch (Exception e) {
            logger.info("DeleteExhibition Command failed");
            session.setAttribute(ERROR_MESSAGE, "cannot delete exhibition");
        }
        String page = req.getHeader("Referer");
        try {
            resp.sendRedirect(page);
        } catch (IOException e) {
            logger.info("DeleteExhibition redirect failed");
        }
    }
}
