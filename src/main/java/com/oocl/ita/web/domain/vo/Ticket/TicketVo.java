package com.oocl.ita.web.domain.vo.Ticket;

import com.oocl.ita.web.domain.po.Ticket.Ticket;
import lombok.Data;

@Data
public class TicketVo {
    private Integer id;
    private String idCardNum;
    private String viewerName;
    private String state;

    public static TicketVo toVo(Ticket ticket) {
        TicketVo ticketVo = new TicketVo();
        ticketVo.setId(ticket.getId());
        ticketVo.setState(ticket.getState());
        ticketVo.setIdCardNum(ticket.getIdCardNum());
        ticketVo.setViewerName(ticket.getViewerName());
        return ticketVo;
    }
}
