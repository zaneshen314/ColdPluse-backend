package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Integer> {
}
