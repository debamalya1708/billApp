package com.dp.billapp.config;

import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {

    @Bean
    public PdfWriter pdfWriter() throws IOException {
        PdfWriter writer = new PdfWriter("output.pdf");
        return writer;
    }
}
