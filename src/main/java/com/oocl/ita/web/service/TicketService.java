package com.oocl.ita.web.service;

import com.oocl.ita.web.domain.vo.Ticket.TicketVo;
import com.oocl.ita.web.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<TicketVo> getTicketsByTransactionId(Integer transactionId) {
        return ticketRepository.findByTransactionId(transactionId).stream().map(TicketVo::toVo).toList();
    }
}
