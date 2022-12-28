package com.exhibit.controller.commands.impl.common;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.connection.ConnectionManager;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.ServiceFactory;
import com.exhibit.dao.constants.DispatchCommand;
import com.exhibit.dao.constants.DispatchType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.dao.constants.UtilConstants.*;


public class GetExhibitions implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp, final ConnectionManager manager) {
        ExhibitionService exhibitionService = ServiceFactory.getInstance().getExhibitionService(manager);
        HttpSession session = req.getSession();

        String sortType = (String) session.getAttribute(SORT_TYPE);
        String sortParam = (String) session.getAttribute(SORT_PARAM);

        if (req.getParameter(SORT_TYPE) != null && req.getParameter(SORT_PARAM) != null) {
            sortType = req.getParameter(SORT_TYPE);
            sortParam = req.getParameter(SORT_PARAM);
            session.setAttribute(SORT_TYPE, sortType);
            session.setAttribute(SORT_PARAM, sortParam);
        }
        if (req.getParameter(SORT_TYPE) != null && req.getParameter(SORT_PARAM) == null) {
            sortType = req.getParameter(SORT_TYPE);
            sortParam = null;
            session.setAttribute(SORT_TYPE, sortType);
            session.removeAttribute(SORT_PARAM);
        }

        //making page to view
        int currentPage = 1;
        if (req.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(req.getParameter(CURRENT_PAGE));
        }

        int amountOfExhibitions = exhibitionService.amountOfExhibitions(sortType, sortParam);
        int amountOfPages = (int) Math.ceil(amountOfExhibitions * 1.0 / RECORDS_PER_PAGE);

        List<Exhibition> exhibitionsList = new CopyOnWriteArrayList<>();
        try {
            exhibitionsList = exhibitionService.findSortByWhereIs(sortType, sortParam, currentPage);
        } catch (Exception e) {
            logger.error(e);
        }

        if (exhibitionsList == null || exhibitionsList.isEmpty()) {
            session.setAttribute(ERROR_MESSAGE, "no exhibition by your request was found");
        } else {
            session.setAttribute(EXHIBITIONS_LIST, exhibitionsList);
            session.setAttribute(AMOUNT_OF_PAGES, amountOfPages);
            session.setAttribute(CURRENT_PAGE, currentPage);
        }

        return new CommandResponse(DispatchType.FORWARD, DispatchCommand.SHOW, EXHIBITIONS_JSP);
    }
}
