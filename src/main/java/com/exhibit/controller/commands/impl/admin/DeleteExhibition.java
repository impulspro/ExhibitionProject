package com.exhibit.controller.commands.impl.admin;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.ServiceFactory;
import com.exhibit.util.constants.DispatchType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.exhibit.util.constants.UtilConstants.*;

public class DeleteExhibition implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String exhibitionId = req.getParameter("exhibitionId");
        ExhibitionService service = ServiceFactory.getInstance().getExhibitionService();
        try {
            service.delete(Long.parseLong(exhibitionId));
            session.setAttribute(USER_MESSAGE, "you deleted exhibition with id =  " + exhibitionId);
        } catch (Exception e) {
            logger.info(e);
            session.setAttribute(ERROR_MESSAGE, "cannot delete exhibition");
        }

        return new CommandResponse(DispatchType.REDIRECT, ADMIN_JSP);
    }
}
