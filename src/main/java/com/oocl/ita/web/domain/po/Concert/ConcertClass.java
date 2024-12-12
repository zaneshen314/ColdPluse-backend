package com.oocl.ita.web.domain.po.Concert;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class ConcertClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String className;

    private Integer concertId;

    private Double priceInUsd;

    private Double priceInLocalCurr;

    private Integer capacity;

    private Integer availableSeats;

    private String currency;

}
