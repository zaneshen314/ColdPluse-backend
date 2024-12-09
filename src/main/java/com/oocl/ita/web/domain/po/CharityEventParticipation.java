package com.oocl.ita.web.domain.po;

import com.oocl.ita.web.CharityEventParticipationStatus;
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

    private boolean claimPoint;

    @Enumerated(EnumType.STRING)
    private CharityEventParticipationStatus status;

    public CharityEventParticipation() {}

    public CharityEventParticipation(Integer userId, Integer charityEventId, CharityEventParticipationStatus status, boolean claimPoint) {
        this.userId = userId;
        this.charityEventId = charityEventId;
        this.status = status;
        this.claimPoint = claimPoint;
    }
}