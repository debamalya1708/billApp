package com.dp.billapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "product")
public class Product {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "serial_no")
    private String serialNo;

    @Column(name = "name")
    private String name;

    @Column(name = "purity")
    private String purity;

    @Column(name = "gross_weight")
    private String grossWeight;

    @Column(name = "net_weight")
    private String netWeight;



}
