package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.ConcertClassBody;
import com.oocl.ita.web.domain.vo.ConcertClassVo;
import com.oocl.ita.web.domain.vo.RespBean;
import com.oocl.ita.web.service.ConcertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    private ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @PostMapping("/{id}/classes")
    public RespBean<ConcertClassVo> addConcertClass(@PathVariable Integer id, @RequestBody ConcertClassBody concertClassBody) {
        return RespBean.success(concertService.addConcertClass(id, concertClassBody));
    }

    @GetMapping("/{id}/classes")
    public RespBean<List<ConcertClassVo>> getConcertClasses(@PathVariable Integer id) {
        return RespBean.success(concertService.listConcertClasses(id));
    }

}
