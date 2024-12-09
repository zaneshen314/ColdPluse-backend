package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.CharityEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharityEventRepository extends JpaRepository<CharityEvent, Integer> {
}