package com.oocl.ita.web.domain.bo;

import lombok.Data;

@Data
public class ConcertClassBody {
    private String className;
    private Integer concertId;
    private Double price;
    private Integer capacity;
    private String currency;
}
