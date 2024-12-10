package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.ReleaseTicketTaskBody;
import com.oocl.ita.web.domain.vo.RespBean;
import com.oocl.ita.web.service.ReleaseTicketTaskService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/release-tickets")
public class ReleaseTicketController {

    private ReleaseTicketTaskService releaseTicketTaskService;

    public ReleaseTicketController(ReleaseTicketTaskService releaseTicketTaskService) {
        this.releaseTicketTaskService = releaseTicketTaskService;
    }

    @PostMapping("/{concertId}/{concertScheduleId}/schedule")
    public RespBean<Boolean> scheduleReleaseTicket(@PathVariable Integer concertId, @PathVariable Integer concertScheduleId
            , @RequestBody ReleaseTicketTaskBody releaseTicketTaskBody) {
        return RespBean.success(releaseTicketTaskService.scheduleReleaseTicketTask(concertId, concertScheduleId, releaseTicketTaskBody));
    }
}
