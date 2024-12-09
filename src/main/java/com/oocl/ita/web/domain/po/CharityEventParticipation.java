package com.oocl.ita.web.domain.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table
public class CharityEventParticipation {

    @Id
    private Integer userId;

    @Id
    private Integer charityEventId;

    private boolean enrolled;

    private boolean finished;

    private boolean closed;

    private boolean claimPoint;

}
