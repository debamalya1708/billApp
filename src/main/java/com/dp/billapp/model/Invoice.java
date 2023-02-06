package com.dp.billapp.model;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "invoice")

@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
        @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})

public class Invoice {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "invoice_id")
    private String invoiceId;

    @Column(name = "invoice_date")
    private String invoiceDate;

    @Column(name = "store_address")
    private String storeAddress;

    //Showroom class

    //bank details

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "s_gst")
    private String sGst;

    @Column(name = "c_gst")
    private String cGst;

    @Column(name = "total_amount")
    private String totalAmount;

    @Type(type = "jsonb")
    @Column(name = "invoice_details", columnDefinition = "jsonb")
    InvoiceDetails invoiceDetails;
}
