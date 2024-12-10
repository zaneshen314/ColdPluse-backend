package com.oocl.ita.web.domain.bo;

import lombok.Data;

import java.util.Date;

@Data
public class ReleaseTicketTaskBody {
    private String startTime;
    private String endTime;
    private Integer repeatCount;
    private Integer hour;
}
