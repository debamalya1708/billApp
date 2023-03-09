package com.dp.billapp.controller;

import com.itextpdf.html2pdf.HtmlConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.file.Files;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value="/mail")
public class MailController {

    @GetMapping("/pdf")
    public void generatePdf() throws IOException, MessagingException {
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
        byte[] response =generatePdfFromHtml(html,"output");


        OutputStream out = new FileOutputStream("out.pdf");
        out.write(response);
        out.close();

        getLocationOfPdf();

    }
    public byte[] generatePdfFromHtml(String html, String name) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(html, buffer);
        byte[] pdfAsBytes = buffer.toByteArray();
        try (FileOutputStream fos = new FileOutputStream(name)) {
            fos.write(pdfAsBytes);
        }
       return pdfAsBytes;
    }
    @Autowired
    private ResourceLoader resourceLoader;

    public void getLocationOfPdf() throws MessagingException, IOException {
        Resource resource = resourceLoader.getResource("./out.pdf");
        File file = null;

        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file != null) {
            System.out.println(file.getAbsolutePath());
        }
        sendMail();
    }

    @Autowired
    private JavaMailSender javaMailSender;

public void sendMail() throws MessagingException, IOException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo("shreyarakshit04@gmail.com");
    helper.setFrom("hippycheeksart@gmail.com");
    helper.setSubject("Test Email");
    helper.setText("This is a test email with an attachment.");

    byte[] fileBytes = Files.readAllBytes(new File("./out.pdf").toPath());
    ByteArrayResource fileResource = new ByteArrayResource(fileBytes);

    helper.addAttachment("out.pdf", fileResource);

    javaMailSender.send(message);
}
}
