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

import javax.persistence.criteria.CriteriaBuilder;
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
    public Invoice saveInvoice(InvoiceRequest invoiceRequest , User employee) {

        final double gst = 1.5;

        Option<User> user = userRepository.findByContact(invoiceRequest.getUserContact());

        Optional<Showroom> showroom = showroomRepository.findById(invoiceRequest.getShowroomId());

        Optional<BankDetails> bank = bankRepository.findById(invoiceRequest.getBankId());

        log.info("#  invoice request - {}", invoiceRequest);

        Invoice invoice = new Invoice();

        invoice.setCreated_By(employee);

        invoice.setUpdated_By(employee);

        Date date = new Date();
        String strDateFormat = "dd/MM/yyyy/hhmmssa";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String formattedDate = dateFormat.format(date);

        invoice.setCreatedAt(formattedDate);

        invoice.setUpdatedAt(formattedDate);

        invoice.setInvoiceDetails(invoiceRequest.getInvoiceDetails());

        invoice.setInvoiceId(generateInvoiceId(invoiceRequest.getInvoiceDate()));

        invoice.setInvoiceDate(invoiceRequest.getInvoiceDate());

        invoice.setShowroom(showroom.get());

        invoice.setBankDetails(bank.get());

        invoice.setCustomer(user.get());

        invoice.setPaymentType(invoiceRequest.getPaymentType());

        invoice.setIsGst(invoiceRequest.getIsGstEnabled());

        if(invoice.getIsGst().equals("0")){
            invoice.setCGst(0);
            invoice.setSGst(0);
        }else{
            invoice.setCGst(gst);
            invoice.setSGst(gst);
        }

        invoice.setTotalAmount(getTotalAmount(invoiceRequest.getInvoiceDetails(),gst, invoiceRequest.getIsGstEnabled()));

        return invoiceDaoService.saveInvoice(invoice);
    }

    @Override
    public List<Invoice> getAllInvoice() {
        return invoiceDaoService.getAllInvoice();
    }

    @Override
    public Optional<Invoice> getInvoiceById(long id) {
        return invoiceDaoService.getInvoiceById(id);
    }

    @Override
    public Optional<Invoice> getInvoiceByInvoiceId(String invoiceId) {
        return invoiceDaoService.getInvoiceByInvoiceId(invoiceId);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        return invoiceDaoService.updateInvoice(invoice);
    }

    @Override
    public String deleteInvoice(long id) {
        invoiceDaoService.deleteInvoice(id);
        return "Invoice deleted";
    }

   public String getTotalAmount(List<InvoiceItem> invoiceDetails, double gst, String isGstEnabled) {
        double allItemAmount = 0 ;
        for(InvoiceItem invoiceItem:invoiceDetails){
            allItemAmount+=Double.parseDouble(invoiceItem.getAmount());
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
