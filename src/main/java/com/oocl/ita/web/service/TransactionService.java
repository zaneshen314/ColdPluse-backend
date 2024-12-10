package com.oocl.ita.web.service;

import com.oocl.ita.web.common.utils.SecurityUtils;
import com.oocl.ita.web.core.exception.EntityNotExistException;
import com.oocl.ita.web.core.exception.NotEnoughTicketsException;
import com.oocl.ita.web.core.exception.TicketLimitExceededException;
import com.oocl.ita.web.domain.bo.OrderTicketBody;
import com.oocl.ita.web.domain.po.ConcertClass;
import com.oocl.ita.web.domain.po.Ticket;
import com.oocl.ita.web.domain.po.Transaction;
import com.oocl.ita.web.domain.vo.TicketVo;
import com.oocl.ita.web.domain.vo.TransactionVo;
import com.oocl.ita.web.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    private TicketService ticketService;

    private TicketRepository ticketRepository;

    private ConcertClassRepository concertClassRepository;

    private ConcertScheduleRepository concertScheduleRepository;

    private ConcertRepository concertRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              TicketService ticketService,
                              TicketRepository ticketRepository,
                              ConcertClassRepository concertClassRepository,
                              ConcertScheduleRepository concertScheduleRepository,
                              ConcertRepository concertRepository) {
        this.transactionRepository = transactionRepository;
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
        this.concertClassRepository = concertClassRepository;
        this.concertScheduleRepository = concertScheduleRepository;
        this.concertRepository = concertRepository;
    }

    public List<TransactionVo> getTransactions() {
        Integer userId = SecurityUtils.getUserId();
        return transactionRepository.findAllByUserId(userId).stream()
                .map(transaction -> {
                    Integer transactionId = transaction.getId();
                    List<Ticket> tickets = ticketRepository.findByTransactionId(transactionId);
                    if (tickets.isEmpty()) {
                        return null;
                    }
                    Integer concertClassId = tickets.get(0).getConcertClassId();
                    Integer concertScheduleId = tickets.get(0).getConcertScheduleId();
                    Integer concertId = tickets.get(0).getConcertId();
                    TransactionVo transactionVo = new TransactionVo();
                    concertClassRepository.findById(concertClassId).ifPresent(concertClass -> {
                        transactionVo.setConcertClassName(concertClass.getClassName());
                    });
                    concertScheduleRepository.findById(concertScheduleId).ifPresent(concertSchedule -> {
                        transactionVo.setStartTime(concertSchedule.getStartTime());
                    });
                    concertRepository.findById(concertId).ifPresent(concert -> {
                        transactionVo.setConcertName(concert.getName());
                    });
                    TransactionVo.toVo(transactionVo, transaction);
                    transactionVo.setTicketVos(ticketService.getTicketsByTransactionId(transaction.getId()));
                    return transactionVo;
                }).toList();
    }


    @Transactional
    public TransactionVo orderTicket(OrderTicketBody orderTicketBody) {
        Integer userId = SecurityUtils.getUserId();
        Integer count = ticketRepository.countByConcertScheduleIdAndUserId(orderTicketBody.getConcertScheduleId(), userId);
        if (count != null && (count + orderTicketBody.getViewers().size() >= 3)) {
            throw new TicketLimitExceededException();
        }
        // TODO 拿锁

        // 要买的区域没票了
        ConcertClass concertClass =
                concertClassRepository.findById(orderTicketBody.getConcertClassId()).orElseThrow(() -> new EntityNotExistException("concertClass"));
        if (concertClass.getAvailableSeats() < orderTicketBody.getViewers().size()) {
            throw new NotEnoughTicketsException();
        }
        // 减库存
        concertClass.setAvailableSeats(concertClass.getAvailableSeats() - orderTicketBody.getViewers().size());
        concertClassRepository.save(concertClass);
        // TODO 放锁

        // 生成订单和票的信息
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setLocalCurrency(concertClass.getCurrency());
        transaction.setAmountInUsd(concertClass.getPriceInUsd() * orderTicketBody.getViewers().size());
        transaction.setAmountInLocalCurrency(concertClass.getPriceInLocalCurr() * orderTicketBody.getViewers().size());
        Transaction saveTransaction = transactionRepository.save(transaction);

        List<Ticket> saveTickets = orderTicketBody.getViewers().stream().map(viewerBody -> {
            Ticket ticket = new Ticket();
            ticket.setConcertClassId(orderTicketBody.getConcertClassId());
            ticket.setConcertScheduleId(orderTicketBody.getConcertScheduleId());
            ticket.setTransactionId(saveTransaction.getId());
            ticket.setUserId(userId);
            ticket.setIdCardNum(viewerBody.getIdCardNum());
            ticket.setViewerName(viewerBody.getName());
            ticket.setConcertId(concertClass.getConcertId());
            ticket.setState("valid");
            return ticketRepository.save(ticket);
        }).toList();

        // 返回
        TransactionVo transactionVo = TransactionVo.toVo(saveTransaction);
        transactionVo.setConcertClassName(concertClass.getClassName());
        transactionVo.setStartTime(concertScheduleRepository.findById(orderTicketBody.getConcertScheduleId()).orElseThrow().getStartTime());
        transactionVo.setConcertName(concertRepository.findById(concertClass.getConcertId()).orElseThrow().getName());
        transactionVo.setTicketVos(saveTickets.stream().map(TicketVo::toVo).toList());
        return transactionVo;
    }
}
