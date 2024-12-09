package com.oocl.ita.web.domain.po;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class CharityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String startTime;

    private Long duration;

    private Integer point;

    private Integer concertId;

    private Integer currentEnrolled;

    private Integer suggestedParticipationSize;

    private String location;

    private String description;

}
