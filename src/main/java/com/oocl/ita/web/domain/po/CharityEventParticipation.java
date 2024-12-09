package com.oocl.ita.web.domain.po;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.oocl.ita.web.domain.po.CharityEventParticipationKey;

@Entity
@Data
@Table
public class CharityEventParticipation {

    @EmbeddedId
    private CharityEventParticipationKey id;

    private boolean enrolled;
    private boolean finished;
    private boolean closed;
    private boolean claimPoint;

    public CharityEventParticipation() {}

    public CharityEventParticipation(Integer userId, Integer charityEventId, boolean enrolled, boolean finished, boolean closed, boolean claimPoint) {
        this.id = new CharityEventParticipationKey(userId, charityEventId);
        this.enrolled = enrolled;
        this.finished = finished;
        this.closed = closed;
        this.claimPoint = claimPoint;
    }
}