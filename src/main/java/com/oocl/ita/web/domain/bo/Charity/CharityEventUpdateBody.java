package com.oocl.ita.web.domain.bo.Charity;

import com.oocl.ita.web.CharityEventParticipationStatus;

public class CharityEventUpdateBody {

    private Integer userId;
    private Integer charityEventId;
    private CharityEventParticipationStatus status;

    public CharityEventUpdateBody(Integer userId, Integer charityEventId) {
        this.userId = userId;
        this.charityEventId = charityEventId;
    }

    public CharityEventUpdateBody() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCharityEventId() {
        return charityEventId;
    }

    public void setCharityEventId(Integer charityEventId) {
        this.charityEventId = charityEventId;
    }

    public CharityEventParticipationStatus getStatus() {
        return status;
    }

    public void setStatus(CharityEventParticipationStatus status) {
        this.status = status;
    }
}
