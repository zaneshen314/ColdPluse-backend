package com.oocl.ita.web.domain.bo;

import lombok.Data;

import java.util.Date;

@Data
public class ReleaseTicketTaskBody {
    private Date startTime;
    private Date endTime;
    private Integer repeatCount;
}
