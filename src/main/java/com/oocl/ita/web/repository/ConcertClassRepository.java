package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.ConcertClass;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertClassRepository extends JpaRepository<ConcertClass, Integer> {
    List<ConcertClass> findByConcertId(Integer concertId);

    @Query(value = "SELECT MAX(c.price_in_usd) FROM concert_class c WHERE c.concert_id = :concertId", nativeQuery = true)
    Double getMaxPriceByConcertId(@Param("concertId") Integer concertId);

    @Query(value = "SELECT MIN(c.price_in_usd) FROM concert_class c WHERE c.concert_id = :concertId", nativeQuery = true)
    Double getMinPriceByConcertId(@Param("concertId") Integer concertId);

}
