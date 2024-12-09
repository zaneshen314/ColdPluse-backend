package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertScheduleRepository extends JpaRepository<ConcertSchedule, Integer> {
    ConcertSchedule findByConcertId(Integer concertId);
}
