package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.exeptions.DaoException;
import com.exhibit.dao.ExhibitionDao;
import com.exhibit.model.Exhibition;
import com.exhibit.services.ExhibitionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class AddExhibitionCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = "view/template/addExhibition.jsp";
        String theme = req.getParameter("theme");
        String details = req.getParameter("details");
        Date startDate = Date.valueOf(req.getParameter("startDate"));
        Date endDate = Date.valueOf(req.getParameter("endDate"));
        Time startTime = Time.valueOf(req.getParameter("startTime") + ":00");
        Time endTime = Time.valueOf(req.getParameter("endTime") + ":00");
        double price = Double.parseDouble(req.getParameter("price"));
        String[] halls_id = req.getParameterValues("halls_id");

        ExhibitionService service = new ExhibitionService();
        Exhibition exhibition = Exhibition.newBuilder()
                .setTheme(theme)
                .setDetails(details)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setPrice(price)
                .build();
        try {
            Exhibition exh = service.findById(1);
            service.addExhibition(exhibition);
            long realExhibitionId = new ExhibitionDao().findByTheme(exhibition.getTheme()).getId();
            service.setHalls(realExhibitionId, halls_id);
            logger.info("AddExhibition command execute successful");
            req.getSession().setAttribute("user_message", "you successfully add exhibition");
        } catch (DaoException e) {
            logger.info("AddExhibition command failed");
            req.getSession().setAttribute("error_message", "something gone wrong");
        }

        resp.sendRedirect(page);
    }
}
