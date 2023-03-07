package com.dp.billapp.service;

import com.dp.billapp.model.InvoiceRequest;
import com.dp.billapp.model.InvoiceResponse;
import com.dp.billapp.model.User;

import java.util.List;

public interface DraftService {

    InvoiceResponse saveInvoice(InvoiceRequest invoiceRequest, User user);
    List<InvoiceResponse> getAllInvoice();
}
