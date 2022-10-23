package com.exhibit.controller.commands.implemantations;

import com.exhibit.controller.commands.Command;
import com.exhibit.model.Exhibition;
import com.exhibit.model.Ticket;
import com.exhibit.model.User;
import com.exhibit.services.ExhibitionService;
import com.exhibit.services.UserService;
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

import static com.exhibit.util.UtilConstants.INFO_LOGGER;

public class PrintPdfCommand implements Command {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        User user = (User) req.getSession().getAttribute("user");
        List<Ticket> ticketList = new UserService().getUserTickets(user);

        if (ticketList == null || ticketList.isEmpty()) {
            String page = req.getHeader("Referer");
            req.getSession().setAttribute("user_message", "you have not tickets yet");
            resp.sendRedirect(page);
        } else {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
            Document doc = new Document(pdfDoc);

            doc.add(new Paragraph("Your tickets dear " + user.getLogin() + ":"));
            ExhibitionService service = new ExhibitionService();

            for (Ticket ticket : ticketList) {
                Optional<Exhibition> exhibitionOpt = service.findById(ticket.getExhibition_id());
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
            logger.info("PrintPdf command execute successful");
            // setting some response headers
            resp.setHeader("Expires", "0");
            resp.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            resp.setHeader("Pragma", "public");


            // setting the content type
            resp.setContentType("application/pdf");
            // the contentlength
            resp.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = resp.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        }

    }
}
