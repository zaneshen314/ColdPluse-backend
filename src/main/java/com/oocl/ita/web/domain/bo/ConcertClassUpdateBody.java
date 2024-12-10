package com.oocl.ita.web.domain.bo;

import lombok.Data;

@Data
public class ConcertClassUpdateBody {
    private String className;
    private Double price;
    private String currency;
}
