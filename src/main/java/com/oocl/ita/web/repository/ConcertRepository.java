package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.Concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Integer> {
}
