package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.ConcertClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertClassRepository extends JpaRepository<ConcertClass, Integer> {
}
