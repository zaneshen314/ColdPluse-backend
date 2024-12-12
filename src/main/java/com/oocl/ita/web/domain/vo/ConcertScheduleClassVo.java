package com.oocl.ita.web.domain.vo;

import lombok.Data;


@Data
public class ConcertScheduleClassVo {
    private Integer id;
    private String concert_schedule_id;
    private Integer concert_class_id;
    private Integer available_seats;
}
