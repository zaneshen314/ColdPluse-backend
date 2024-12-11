package com.oocl.ita.web.domain.bo.Concert;

import lombok.Data;

@Data
public class ConcertClassBody {
    private String className;
    private Double price;
    private Integer capacity;
    private String currency;
}
