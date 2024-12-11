package com.oocl.ita.web.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ConcertScheduleVo {
    private Integer scheduleId;
    private String strartTime;
    private List<ConcertClassVo> concertClasses;
}
