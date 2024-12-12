package com.oocl.ita.web.domain.po.Concert;

import com.oocl.ita.web.domain.bo.Concert.ConcertScheduleRegBody;
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

    private String saleStartTime;

    public ConcertSchedule() {
    }

    public ConcertSchedule(ConcertScheduleRegBody concertScheduleRegBody) {
        this.concertId = concertScheduleRegBody.getConcertId();
        this.startTime = concertScheduleRegBody.getStartTime();
        this.duration = concertScheduleRegBody.getDuration();
        this.saleStartTime = concertScheduleRegBody.getSaleStartTime();
    }

}
