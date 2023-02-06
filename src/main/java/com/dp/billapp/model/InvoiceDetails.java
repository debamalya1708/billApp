package com.dp.billapp.model;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceDetails {
    User user;
    List<InvoiceItem> invoiceItemList;
}
