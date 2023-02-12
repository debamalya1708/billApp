package com.dp.billapp.service;

import com.dp.billapp.model.Invoice;
import com.dp.billapp.model.InvoiceRequest;

import java.util.List;

public interface InvoiceService {

    Invoice saveInvoice(InvoiceRequest invoiceRequest);
    List<Invoice> getAllInvoice();

}
