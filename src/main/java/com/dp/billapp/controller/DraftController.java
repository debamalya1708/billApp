package com.dp.billapp.controller;


import com.dp.billapp.model.*;
import com.dp.billapp.repository.BankRepository;
import com.dp.billapp.repository.ShowroomRepository;
import com.dp.billapp.repository.UserRepository;
import com.dp.billapp.service.DraftService;
import com.dp.billapp.service.InvoiceService;
import com.dp.billapp.service.UserService;
import com.dp.billapp.serviceImpl.InvoiceServiceImpl;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value="/draft")
public class DraftController {

    @Autowired
    DraftService draftService;

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShowroomRepository showroomRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    InvoiceServiceImpl invoiceServiceImpl;


    @PostMapping("/save")
    public ResponseEntity<?> saveDraft(@RequestBody InvoiceRequest invoiceRequest, HttpServletRequest request){

        if(request.getContentLength()==0)
            return  new ResponseEntity<>("Token Not Found!!!", HttpStatus.NOT_FOUND);
        String userContact= userService.getContact(request);
        Option<User> userOptional = userService.findByContact(userContact);


        InvoiceResponse invoice = draftService.saveInvoice(invoiceRequest , userOptional.get());

        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/all")
    public ResponseEntity<?> allInvoice(){
        return ResponseEntity.ok(draftService.getAllInvoice());
    }

    @GetMapping("/search/draft/{invoiceId}")
    public ResponseEntity<?> findDraftById(@PathVariable String invoiceId){
        InvoiceResponse invoiceOptional =draftService.getDraftInvoiceById(invoiceId);
        if(invoiceOptional == null)
            return new ResponseEntity<>("Draft Not exists!", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(invoiceOptional);
    }

}
