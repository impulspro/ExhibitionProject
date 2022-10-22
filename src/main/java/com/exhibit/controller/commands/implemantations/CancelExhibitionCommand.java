package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.services.ExhibitionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class CancelExhibitionCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String exhibition_id = req.getParameter("exhibition_id");
        ExhibitionService service = new ExhibitionService();
        try {
            service.cancelExhibition(Long.parseLong(exhibition_id));
            logger.info("CancelExhibition Command successfully");
            session.setAttribute("user_message", "you canceled exhibition with id =  " + exhibition_id);
        } catch (Exception e) {
            logger.info("CancelExhibition Command failed");
            session.setAttribute("error_message", "cannot cancel exhibition");
        }
        String page = req.getHeader("Referer");
        resp.sendRedirect(page);
    }
}
