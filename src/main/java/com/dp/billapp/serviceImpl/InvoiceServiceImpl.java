package com.dp.billapp.serviceImpl;

import com.dp.billapp.daoService.InvoiceDaoService;
import com.dp.billapp.model.*;
import com.dp.billapp.repository.BankRepository;
import com.dp.billapp.repository.ShowroomRepository;
import com.dp.billapp.repository.UserRepository;
import com.dp.billapp.service.InvoiceService;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceDaoService invoiceDaoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowroomRepository showroomRepository;

    @Autowired
    private BankRepository bankRepository;

    @Override
    public Invoice saveInvoice(InvoiceRequest invoiceRequest) {

        Option<User> user = userRepository.findByContact(invoiceRequest.getUserContact());

        Optional<Showroom> showroom = showroomRepository.findById(invoiceRequest.getShowroomId());

        Optional<BankDetails> bank = bankRepository.findById(invoiceRequest.getBankId());

        log.info("#  invoice request - {}", invoiceRequest);

        Invoice invoice = new Invoice();

        invoice.setInvoiceDetails(invoiceRequest.getInvoiceDetails());

        invoice.setInvoiceId(generateInvoiceId(invoiceRequest.getInvoiceDate()));

        invoice.setInvoiceDate(invoiceRequest.getInvoiceDate());

        invoice.setShowroom(showroom.get());

        invoice.setBankDetails(bank.get());

        invoice.setCustomer(user.get());

        invoice.setPaymentType(invoiceRequest.getPaymentType());

        invoice.setCGst(1.5);

        invoice.setSGst(1.5);

        invoice.setTotalAmount(getTotalAmount(invoiceRequest.getInvoiceDetails(),1.5, invoiceRequest.getIsGstEnabled()));

        return invoiceDaoService.saveInvoice(invoice);
    }

    private String getTotalAmount(List<InvoiceItem> invoiceDetails, double gst, String isGstEnabled) {
        double allItemAmount = 0 ;
        for(InvoiceItem invoiceItem:invoiceDetails){
            allItemAmount+=invoiceItem.getAmount();
        }
        double afterGstCalculation = 0;
        if(isGstEnabled.equals("1")){
            double gstAmount = (allItemAmount*gst)/100;
            afterGstCalculation = gstAmount * 2;
        }
        double finalAmount = allItemAmount + afterGstCalculation;
        return Double.toString(finalAmount);
    }


    private String generateInvoiceId(String invoiceDate){
        Date date = new Date();
        String strDateFormat = "hhmmss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String formattedDate = dateFormat.format(date);
        String invoiceId = invoiceDate+"_"+formattedDate;
        return invoiceId;
    }
}
