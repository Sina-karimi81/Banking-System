package com.github.bankingsystem.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BankAccount implements Serializable {

    @Id
    @Column(name = "Account_Id")
    private String id;
    @Column(name = "Holder_Name", nullable = false)
    private String name;
    @Column(name = "Amount", nullable = false)
    private Float amount;

}
