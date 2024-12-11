package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.ConcertClassBody;
import com.oocl.ita.web.domain.bo.ConcertClassUpdateBody;
import com.oocl.ita.web.domain.bo.ConcertScheduleRegBody;
import com.oocl.ita.web.domain.po.ConcertSchedule;
import com.oocl.ita.web.domain.vo.ConcertClassVo;
import com.oocl.ita.web.domain.vo.ConcertSessionVo;
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

    @PostMapping("/{concertId}/classes")
    public RespBean<ConcertClassVo> addConcertClass(@PathVariable Integer concertId, @RequestBody ConcertClassBody concertClassBody) {
        return RespBean.success(concertService.addConcertClass(concertId, concertClassBody));
    }

    @GetMapping("/{concertId}/schedules/{scheduleId}/classes")
    public RespBean<List<ConcertClassVo>> getConcertClasses(@PathVariable Integer concertId, @PathVariable Integer scheduleId) {
        return RespBean.success(concertService.listConcertClasses(concertId, scheduleId));
    }

    @PutMapping("/{concertId}/classes/{classId}")
    public RespBean<ConcertClassVo> updateConcertClass(@PathVariable Integer concertId,
                                                       @PathVariable Integer classId,
                                                       @RequestBody ConcertClassUpdateBody concertClassBody) {
        return RespBean.success(concertService.updateConcertClass(concertId, classId, concertClassBody));
    }
    @GetMapping("/{id}/schedules/{scheduleId}")
    public RespBean<ConcertSessionVo> getConcertSession(@PathVariable Integer scheduleId, @PathVariable String id) {
        return RespBean.success(concertService.getConcertSession(scheduleId));
    }

    @GetMapping("/schedules")
    public RespBean<List<ConcertSessionVo>> getAllConcertSessions() {
        return RespBean.success(concertService.getAllConcertSessions());
    }

    @GetMapping("/{concertId}/schedules")
    public RespBean<List<ConcertSessionVo>> getConcertSessionsByConcertId(@PathVariable Integer concertId) {
        return RespBean.success(concertService.getConcertSessionsByConcertId(concertId));
    }

    @PostMapping("/schedules")
    public ConcertSchedule addConcertSchedule(@RequestBody ConcertScheduleRegBody concertScheduleRegBody) {
        return concertService.addCharitySchedule(concertScheduleRegBody);
    }

}
