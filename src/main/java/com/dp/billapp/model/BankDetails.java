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
<<<<<<< HEAD
@Table(name = "bank")
=======
@Table(name = "bank_details")
>>>>>>> origin/Showroom_Details_and_Bank_Details
public class BankDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "account_number")
    private String accountNumber;

<<<<<<< HEAD
    @Column(name = "ifsc_Code")
=======
    @Column(name = "IFSC_Code")
>>>>>>> origin/Showroom_Details_and_Bank_Details
    private String ifscCode;
}
