package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByTransactionId(Integer transactionId);

    Integer countByConcertScheduleIdAndUserId(Integer concertScheduleId, Integer userId);
}
