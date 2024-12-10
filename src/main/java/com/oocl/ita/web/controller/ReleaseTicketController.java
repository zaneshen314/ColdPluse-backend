package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.ReleaseTicketTaskBody;
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
    public void scheduleReleaseTicket(@PathVariable Integer concertId, @PathVariable Integer concertScheduleId
            , @RequestBody ReleaseTicketTaskBody releaseTicketTaskBody) {
        releaseTicketTaskService.scheduleReleaseTicketTask(concertId, concertScheduleId, releaseTicketTaskBody);
    }
}
