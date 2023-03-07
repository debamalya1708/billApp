package com.dp.billapp.daoService;

import com.dp.billapp.model.Draft;
import com.dp.billapp.model.Invoice;

import java.util.List;

public interface DraftDaoService {
    Draft saveInvoice(Draft draft);
    List<Draft> getAllInvoice();
}
