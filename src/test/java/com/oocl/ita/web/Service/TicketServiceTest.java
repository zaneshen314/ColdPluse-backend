package com.oocl.ita.web.Service;

import com.oocl.ita.web.domain.po.Ticket.Ticket;
import com.oocl.ita.web.domain.vo.Ticket.TicketVo;
import com.oocl.ita.web.repository.TicketRepository;
import com.oocl.ita.web.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_valid_transaction_id_when_get_tickets_by_transaction_id_then_return_ticket_list() {
        // given
        Integer transactionId = 1;
        Ticket ticket = new Ticket();
        when(ticketRepository.findByTransactionId(transactionId)).thenReturn(List.of(ticket));

        // when
        List<TicketVo> result = ticketService.getTicketsByTransactionId(transactionId);

        // then
        assertEquals(1, result.size());
        verify(ticketRepository).findByTransactionId(transactionId);
    }
}