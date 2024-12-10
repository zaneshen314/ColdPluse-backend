package com.oocl.ita.web.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class OrderTicketBody {
    private Integer concertClassId;
    private Integer concertScheduleId;
    private List<ViewerBody> viewers;
}
