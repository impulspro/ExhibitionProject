package com.exhibit.controller.commands.impl.user;

import com.exhibit.controller.commands.Command;
import com.exhibit.controller.commands.CommandResponse;
import com.exhibit.dao.connection.ConnectionManager;
import com.exhibit.dao.model.Exhibition;
import com.exhibit.dao.model.Ticket;
import com.exhibit.dao.model.User;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;
import com.exhibit.dao.constants.DispatchCommand;
import com.exhibit.dao.constants.DispatchType;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.exhibit.dao.constants.UtilConstants.*;

public class PrintPdf implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public CommandResponse execute(final HttpServletRequest req, final HttpServletResponse resp, final ConnectionManager manager) {
        UserService userService = ServiceFactory.getInstance().getUserService(manager);
        ExhibitionService exhibitionService = ServiceFactory.getInstance().getExhibitionService(manager);

        User user = (User) req.getSession().getAttribute("user");
        List<Ticket> ticketList = userService.getUserTickets(user.getId());

        if (ticketList == null || ticketList.isEmpty()) {
            req.getSession().setAttribute(USER_MESSAGE, "you have not tickets yet");
            return new CommandResponse(DispatchType.FORWARD, HOME_PAGE);
        } else {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
                Document doc = new Document(pdfDoc);

                doc.add(new Paragraph("Your tickets dear " + user.getLogin() + ":"));


                for (Ticket ticket : ticketList) {
                    Optional<Exhibition> exhibitionOpt = exhibitionService.findById(ticket.getExhibitionId());
                    if (exhibitionOpt.isPresent()) {
                        Exhibition exhibition = exhibitionOpt.get();
                        String theme = exhibition.getTheme();
                        Date startDate = exhibition.getStartDate();
                        Date endDate = exhibition.getEndDate();
                        Time startTime = exhibition.getStartTime();
                        Time endTime = exhibition.getEndTime();
                        double price = exhibition.getPrice();
                        String builder = theme + " " + startDate + " " + endDate +
                                " " + startTime + " " + endTime +
                                " " + price + "$";
                        doc.add(new Paragraph(builder));
                        ImageData imageData = ImageDataFactory.create("https://images.vexels.com/media/users/3/157843/isolated/preview/407fd12952bc44e96535cea25d9f29e1-bar-code-label-element.png");
                        Image image = new Image(imageData).scaleToFit(500, 150);
                        doc.add(image);
                    }
                }

                doc.close();
             // write ByteArrayOutputStream to the ServletOutputStream
                OutputStream os = resp.getOutputStream();
                baos.writeTo(os);
                os.flush();
                os.close();
            } catch (IOException e) {
                logger.error(e);
                req.getSession().setAttribute(ERROR_MESSAGE, "problems with creating pdf");
            }
        }

        return new CommandResponse(DispatchType.FORWARD, DispatchCommand.STAY);
    }
}
