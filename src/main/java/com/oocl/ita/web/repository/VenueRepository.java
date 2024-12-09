package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Integer> {
}
