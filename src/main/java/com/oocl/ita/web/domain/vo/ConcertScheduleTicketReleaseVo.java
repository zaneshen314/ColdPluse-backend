package com.oocl.ita.web.domain.vo;

import lombok.Data;


@Data
public class ConcertScheduleTicketReleaseVo {
    private Integer concertId;
    private String concertName;
    private ConcertScheduleVo concertSchedule;
    private TicketReleaseVo ticketRelease;
}
