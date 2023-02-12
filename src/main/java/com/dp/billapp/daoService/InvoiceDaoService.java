package com.dp.billapp.daoService;

import com.dp.billapp.model.Invoice;

import java.util.List;

public interface InvoiceDaoService {

    Invoice saveInvoice(Invoice invoice);
    List<Invoice> getAllInvoice();
}
