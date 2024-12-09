package com.oocl.ita.web.domain.po;

import com.oocl.ita.web.domain.po.key.CharityEventParticipationKey;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table
@IdClass(CharityEventParticipationKey.class)
public class CharityEventParticipation {

    @Id
    private Integer userId;
    @Id
    private Integer charityEventId;

    private boolean enrolled;
    private boolean finished;
    private boolean closed;
    private boolean claimPoint;

    public CharityEventParticipation() {}

    public CharityEventParticipation(Integer userId, Integer charityEventId, boolean enrolled, boolean finished, boolean closed, boolean claimPoint) {
        this.userId = userId;
        this.charityEventId = charityEventId;
        this.enrolled = enrolled;
        this.finished = finished;
        this.closed = closed;
        this.claimPoint = claimPoint;
    }
}