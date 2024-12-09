package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertScheduleRepository extends JpaRepository<ConcertSchedule, Integer> {
}
