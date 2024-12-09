package com.oocl.ita.web.domain.po;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class ConcertSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer concertId;

    private String startTime;

    private Long duration;

}
