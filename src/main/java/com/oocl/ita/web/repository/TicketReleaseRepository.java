package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.Ticket.TicketRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketReleaseRepository extends JpaRepository<TicketRelease, Integer> {
    TicketRelease findByConcertScheduleId(Integer concertScheduleId);
}
