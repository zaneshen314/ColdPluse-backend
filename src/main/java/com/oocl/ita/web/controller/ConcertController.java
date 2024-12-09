package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.ConcertClassBody;
import com.oocl.ita.web.domain.vo.ConcertClassVo;
import com.oocl.ita.web.domain.vo.RespBean;
import com.oocl.ita.web.service.ConcertService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/concert")
public class ConcertController {

    private ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @PostMapping("/concertClass")
    public RespBean<ConcertClassVo> addConcertClass(@RequestBody ConcertClassBody concertClassBody) {
        return RespBean.success(concertService.addConcertClass(concertClassBody));
    }


}
