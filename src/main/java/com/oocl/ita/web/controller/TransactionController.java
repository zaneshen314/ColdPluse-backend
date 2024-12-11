package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.Ticket.OrderTicketBody;
import com.oocl.ita.web.domain.vo.RespBean;
import com.oocl.ita.web.domain.vo.Ticket.TransactionVo;
import com.oocl.ita.web.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public RespBean<List<TransactionVo>> getTransactions() {
        return RespBean.success(transactionService.getTransactions());
    }

    @PostMapping
    public RespBean<TransactionVo> orderTicket(@RequestBody OrderTicketBody orderTicketBody) {
        return RespBean.success(transactionService.orderTicket(orderTicketBody));
    }
}
