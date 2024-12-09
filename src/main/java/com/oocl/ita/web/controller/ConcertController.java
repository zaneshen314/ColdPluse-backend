package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.ConcertClassBody;
import com.oocl.ita.web.domain.vo.ConcertClassVo;
import com.oocl.ita.web.domain.vo.RespBean;
import com.oocl.ita.web.service.ConcertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concert")
public class ConcertController {

    private ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @PostMapping("/concertClasses")
    public RespBean<ConcertClassVo> addConcertClass(@RequestBody ConcertClassBody concertClassBody) {
        return RespBean.success(concertService.addConcertClass(concertClassBody));
    }

    @GetMapping("/concertClasses")
    public RespBean<List<ConcertClassVo>> getConcertClasses() {
        return RespBean.success(concertService.listConcertClasses());
    }

}
