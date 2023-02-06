package com.dp.billapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.SpringSecurityCoreVersion;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InvoiceItem {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private long NoOfPieces;
    private String title;
    private long hsnCode;
    private long purity;
    private String grossWeight;
    private String netWeight;
    private double rateOfGoldPerGram;
    private double valueOfGold;
    private double makingCharges;
    private double hallMarkingCharges;
    private double amount;

}
