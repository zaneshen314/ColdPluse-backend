package com.oocl.ita.web.domain.bo.Ticket;

import lombok.Data;

@Data
public class ReleaseTicketTaskBody {
    private String startTime;
    private String endTime;
    private Integer repeatCount;
    private Integer hour;
}
