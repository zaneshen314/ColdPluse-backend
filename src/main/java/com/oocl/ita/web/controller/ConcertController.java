package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.Concert.ConcertClassBody;
import com.oocl.ita.web.domain.bo.Concert.ConcertClassUpdateBody;
import com.oocl.ita.web.domain.bo.Concert.ConcertScheduleRegBody;
import com.oocl.ita.web.domain.po.Concert.Concert;
import com.oocl.ita.web.domain.po.Concert.ConcertSchedule;
import com.oocl.ita.web.domain.po.ConcertScheduleClass;
import com.oocl.ita.web.domain.vo.*;
import com.oocl.ita.web.domain.vo.Concert.ConcertClassVo;
import com.oocl.ita.web.domain.vo.Concert.ConcertSessionVo;
import com.oocl.ita.web.domain.vo.Concert.ConcertVo;
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

    @GetMapping
    public RespBean<List<Concert>> getAllConcerts() {
        return RespBean.success(concertService.getAllConcerts());
    }

    @PostMapping("/{concertId}/classes")
    public RespBean<ConcertClassVo> addConcertClass(@PathVariable Integer concertId, @RequestBody ConcertClassBody concertClassBody) {
        return RespBean.success(concertService.addConcertClass(concertId, concertClassBody));
    }

    @GetMapping("/{concertId}/classes")
    public RespBean<List<ConcertClassVo>> getConcertClasses(@PathVariable Integer concertId) {
        return RespBean.success(concertService.listConcertClasses(concertId));
    }

    @PutMapping("/{concertId}/schedules/{scheduleId}/classes/{classId}")
    public RespBean<ConcertClassVo> updateConcertClass(@PathVariable Integer concertId,
                                                       @PathVariable Integer scheduleId,
                                                       @PathVariable Integer classId,
                                                       @RequestBody ConcertClassUpdateBody concertClassBody) {
        return RespBean.success(concertService.updateConcertClass(concertId, scheduleId, classId, concertClassBody));
    }
    @GetMapping("/{id}/schedules/{scheduleId}")
    public RespBean<ConcertSessionVo> getConcertScheduleByConcertIdAndScheduleId(@PathVariable Integer scheduleId, @PathVariable String id) {
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

    @GetMapping("/{concertId}")
    public RespBean<ConcertVo>getConcertDetailByConcertId(@PathVariable Integer concertId){
        return RespBean.success(concertService.getConcertByConcertId(concertId));
    }

    @PostMapping("/schedules")
    public ConcertSchedule addConcertSchedule(@RequestBody ConcertScheduleRegBody concertScheduleRegBody) {
        return concertService.addConcertSchedule(concertScheduleRegBody);
    }

    @GetMapping("/{concertId}/schedules/{scheduleId}/concert_schedule_classes")
    public RespBean<List<ConcertScheduleClass>> getAllConcertScheduleClassesByConcertScheduleId(@PathVariable Integer concertId, @PathVariable Integer scheduleId) {
        return RespBean.success(concertService.listConcertScheduleClasses(scheduleId));
    }

}
