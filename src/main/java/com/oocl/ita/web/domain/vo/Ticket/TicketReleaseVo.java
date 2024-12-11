package com.oocl.ita.web.domain.vo.Ticket;

import com.oocl.ita.web.domain.po.Ticket.TicketRelease;
import lombok.Data;

@Data
public class TicketReleaseVo {
    private String startTime;
    private String endTime;
    private Integer hour;
    private Integer repeatCount;

    public static TicketReleaseVo toVo(TicketRelease ticketRelease) {
        TicketReleaseVo ticketReleaseVo = new TicketReleaseVo();
        ticketReleaseVo.setStartTime(ticketRelease.getStartTime());
        ticketReleaseVo.setEndTime(ticketRelease.getEndTime());
        ticketReleaseVo.setHour(ticketRelease.getHour());
        ticketReleaseVo.setRepeatCount(ticketRelease.getFrequency());
        return ticketReleaseVo;
    }
}
