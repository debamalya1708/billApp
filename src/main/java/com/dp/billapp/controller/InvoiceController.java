package com.dp.billapp.controller;

import com.dp.billapp.model.Invoice;
import com.dp.billapp.model.InvoiceRequest;
import com.dp.billapp.model.Product;
import com.dp.billapp.model.Showroom;
import com.dp.billapp.service.InvoiceService;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/all")
    public ResponseEntity<?> allInvoice(){
        return ResponseEntity.ok(invoiceService.getAllInvoice());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findInvoiceById(@PathVariable long id){
        Optional<Invoice> invoiceOptional =invoiceService.getInvoiceById(id);
        if(invoiceOptional.isEmpty())
            return new ResponseEntity<>("Invoice Not exists!", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(invoiceOptional);
    }
    @GetMapping("/search/invoice/{invoiceId}")
    public ResponseEntity<?> findInvoiceById(@PathVariable String invoiceId){
        Optional<Invoice> invoiceOptional =invoiceService.getInvoiceByInvoiceId(invoiceId);
        if(invoiceOptional.isEmpty())
            return new ResponseEntity<>("Invoice Not exists!", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(invoiceOptional);
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateInvoice(@RequestBody Invoice invoice){
        Optional<Invoice> invoiceOptional = invoiceService.getInvoiceById(invoice.getId());
        if(invoiceOptional.isEmpty())
            return new ResponseEntity<>("Invoice Not exists!", HttpStatus.NOT_FOUND);


        return ResponseEntity.ok(invoiceService.updateInvoice(invoice));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable long id){
        Optional<Invoice> invoice =invoiceService.getInvoiceById(id);
        if(invoice.isEmpty())
            return new ResponseEntity<>("Invoice doesn't exist,can't be deleted",HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(invoiceService.deleteInvoice(id));
    }



}
