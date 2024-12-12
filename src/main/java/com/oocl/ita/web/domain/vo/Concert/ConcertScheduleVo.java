package com.oocl.ita.web.domain.vo.Concert;

import lombok.Data;

import java.util.List;

@Data
public class ConcertScheduleVo {
    private Integer scheduleId;
    private String strartTime;
    private String saleStartTime;
    private List<ConcertClassVo> concertClasses;
}
