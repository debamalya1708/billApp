package com.dp.billapp.controller;

import com.dp.billapp.model.Invoice;
import com.dp.billapp.model.InvoiceRequest;
import com.dp.billapp.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/save")
    public ResponseEntity<?> saveInvoice(@RequestBody InvoiceRequest invoiceRequest){
        Invoice invoice = invoiceService.saveInvoice(invoiceRequest);

        return ResponseEntity.ok(invoice);
    }

}
