package com.oocl.ita.web.domain.po;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Double amountInUsd;

    @Column(name = "amount_in_local_curr")
    private Double amountInLocalCurrency;

    @Column(name = "local_curr")
    private String localCurrency;
}
