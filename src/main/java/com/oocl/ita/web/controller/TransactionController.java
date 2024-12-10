package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.OrderTicketBody;
import com.oocl.ita.web.domain.vo.RespBean;
import com.oocl.ita.web.domain.vo.TransactionVo;
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

    @GetMapping("/{userId}")
    public RespBean<List<TransactionVo>> getTransactions(@PathVariable Integer userId) {
        return RespBean.success(transactionService.getTransactions(userId));
    }

    @PostMapping("/{userId}")
    public RespBean<TransactionVo> orderTicket(@PathVariable Integer userId, @RequestBody OrderTicketBody orderTicketBody) {
        return RespBean.success(transactionService.orderTicket(userId, orderTicketBody));
    }
}
