package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.CharityEventParticipation;
import com.oocl.ita.web.domain.po.CharityEventParticipationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharityEventParticipationRepository extends JpaRepository<CharityEventParticipation, CharityEventParticipationKey> {

    public List<CharityEventParticipation> findAllByUserId(Integer userId);

    public List<CharityEventParticipation> findAllByCharityEventId(Integer charityEventId);
}
