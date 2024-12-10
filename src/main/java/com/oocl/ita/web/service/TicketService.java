package com.oocl.ita.web.service;

import com.oocl.ita.web.core.exception.EntityNotExistException;
import com.oocl.ita.web.domain.po.Concert;
import com.oocl.ita.web.domain.po.ConcertClass;
import com.oocl.ita.web.domain.po.ConcertSchedule;
import com.oocl.ita.web.domain.po.Ticket;
import com.oocl.ita.web.domain.vo.TicketVo;
import com.oocl.ita.web.repository.ConcertClassRepository;
import com.oocl.ita.web.repository.ConcertRepository;
import com.oocl.ita.web.repository.ConcertScheduleRepository;
import com.oocl.ita.web.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
