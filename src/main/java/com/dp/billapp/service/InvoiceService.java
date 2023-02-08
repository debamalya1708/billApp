package com.dp.billapp.service;

import com.dp.billapp.model.Invoice;
import com.dp.billapp.model.InvoiceRequest;

public interface InvoiceService {

    Invoice saveInvoice(InvoiceRequest invoiceRequest);

}
