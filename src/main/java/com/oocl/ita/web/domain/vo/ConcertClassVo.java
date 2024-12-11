package com.oocl.ita.web.domain.vo;

import lombok.Data;

@Data
public class ConcertClassVo {

    private Integer id;
    private String className;
    private Double price;
    private Integer capacity;
    private Integer availableSeats;
    private Integer concertId;
    private Integer concertScheduleId;
}
