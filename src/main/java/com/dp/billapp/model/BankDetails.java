package com.dp.billapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "bank")
public class BankDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "IFSC_Code")
    private String ifscCode;
}
