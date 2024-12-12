package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.Charity.CharityEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CharityEventRepository extends JpaRepository<CharityEvent, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE CharityEvent c SET c.currentEnrolled = :currentEnrolled WHERE c.id = :eventId")
    public void updateCurrentEnrolledById(Integer eventId, Integer currentEnrolled);
}
