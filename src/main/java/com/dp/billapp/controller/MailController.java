package com.dp.billapp.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value="/mail")
public class MailController {

//    @Autowired
//    ServletContext servletContext;
//
//    @Autowired
//    private final TemplateEngine templateEngine;

//    public MailController(TemplateEngine templateEngine) {
//        this.templateEngine = templateEngine;
//    }


    private final PdfWriter pdfWriter;

    @PostMapping(path = "/pdf")
    public void getPDF() throws IOException {

//        /* Do Business Logic*/
//
////        Order order = OrderHelper.getOrder();
//
//        /* Create HTML using Thymeleaf template Engine */
//
//        WebContext context = new WebContext(request,response,servletContext);
////        context.setVariable("orderEntry", order);
//        String orderHtml = templateEngine.process("invoice", context);
//
//        /* Setup Source and target I/O streams */
//
//        ByteArrayOutputStream target = new ByteArrayOutputStream();
//
//        /*Setup converter properties. */
//        ConverterProperties converterProperties = new ConverterProperties();
//        converterProperties.setBaseUri("http://localhost:8081");
//
//        /* Call convert method */
//        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);
//
//        /* extract output as bytes */
//        byte[] bytes = target.toByteArray();
//
//
//        /* Send the response as downloadable PDF */
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(bytes);

        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h2>Delivery Address</h2>\n" +
                "</body>\n" +
                "</html>";


        System.out.print(HtmlConverter.convertToDocument(html,pdfWriter));

    }
}
