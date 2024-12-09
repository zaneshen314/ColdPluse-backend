package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.ConcertClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface ConcertClassRepository extends JpaRepository<ConcertClass, Integer> {
    List<ConcertClass> findByConcertId(Integer concertId);
}
