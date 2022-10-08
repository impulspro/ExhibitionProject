package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.model.Hall;
import com.exhibit.model.User;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.HallService;
import com.exhibit.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class GetHallsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = "view/halls.jsp";
        try {
            HallService service = new HallService();
            List<Hall> hallList = service.findAll();
            req.getSession().setAttribute("hallList", hallList);
            logger.info("GetHalls command successful");
        } catch (Exception e) {
            logger.info("GetHalls command failed");
            page = "index.jsp";
            req.getSession().setAttribute("error_message", "GetHalls command failed");
        }
        resp.sendRedirect(page);
    }
}
