package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertScheduleRepository extends JpaRepository<ConcertSchedule, Integer> {
    List<ConcertSchedule> findByConcertIdOrderByStartTimeAsc(Integer concertId);
}
