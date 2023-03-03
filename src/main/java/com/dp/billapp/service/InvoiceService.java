package com.dp.billapp.service;

import com.dp.billapp.model.Invoice;
import com.dp.billapp.model.InvoiceRequest;
import com.dp.billapp.model.UpdateInvoiceRequest;
import com.dp.billapp.model.User;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    Invoice saveInvoice(InvoiceRequest invoiceRequest, User user);
    List<Invoice> getAllInvoice();
    Optional<Invoice> getInvoiceById(long id);
    Optional<Invoice> getInvoiceByInvoiceId(String invoiceId);
    Invoice updateInvoice(Invoice invoice);
    String deleteInvoice(long id);

}
