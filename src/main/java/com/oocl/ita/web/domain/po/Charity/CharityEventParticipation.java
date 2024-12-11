package com.oocl.ita.web.domain.po.Charity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oocl.ita.web.common.constant.CharityEventParticipationStatus;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table
@IdClass(CharityEventParticipationKey.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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