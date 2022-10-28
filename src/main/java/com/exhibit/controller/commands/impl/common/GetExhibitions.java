package com.exhibit.controller.commands.impl.common;

import com.exhibit.controller.commands.Command;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.exhibit.util.constants.UtilConstants.*;


public class GetExhibitions implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(final HttpServletRequest req, final HttpServletResponse resp) {
        ExhibitionService service = ServiceFactory.getInstance().getExhibitionService();
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

        int amountOfExhibitions = service.amountOfExhibitions(sortType, sortParam);
        int amountOfPages = (int) Math.ceil(amountOfExhibitions * 1.0 / RECORDS_PER_PAGE);
        List<Exhibition> exhibitionsList = service.findSortByWhereIs(sortType, sortParam, currentPage);

        String redirect;
        if (exhibitionsList == null || exhibitionsList.isEmpty()) {
            session.setAttribute(ERROR_MESSAGE, "no exhibition by your request was found");
            redirect = req.getHeader("Referer");
        } else {
            redirect = EXHIBITION_PAGE;
            session.setAttribute(EXHIBITIONS_LIST, exhibitionsList);
            session.setAttribute(AMOUNT_OF_PAGES, amountOfPages);
            session.setAttribute(CURRENT_PAGE, currentPage);
        }

        try {
            resp.sendRedirect(redirect);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
