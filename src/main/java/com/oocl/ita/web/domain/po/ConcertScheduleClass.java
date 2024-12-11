package com.oocl.ita.web.domain.po;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class ConcertScheduleClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer concertScheduleId;
    private Integer concertClassId;
    private Integer availableSeats;

    public ConcertScheduleClass() {
    }

    public ConcertScheduleClass(Integer concertScheduleId, Integer concertClassId) {
        this.concertScheduleId = concertScheduleId;
        this.concertClassId = concertClassId;
        this.availableSeats = 0;
    }

}
