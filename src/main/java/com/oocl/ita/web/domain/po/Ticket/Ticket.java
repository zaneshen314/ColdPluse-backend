package com.oocl.ita.web.domain.po.Ticket;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer concertClassId;

    private Integer concertScheduleId;

    private Integer userId;

    private String idCardNum;

    private String state;

    private Integer transactionId;

    private Integer concertId;

    private String viewerName;
}
