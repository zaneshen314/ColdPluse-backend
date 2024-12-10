package com.oocl.ita.web.domain.vo;

import com.oocl.ita.web.domain.po.CharityEvent;
import com.oocl.ita.web.domain.po.CharityEventParticipation;

public class UserCharityEventParticipationResp {

    private CharityEventParticipation charityEventParticipation;
    private CharityEvent charityEvent;

    public UserCharityEventParticipationResp() {
    }

    public UserCharityEventParticipationResp(CharityEventParticipation charityEventParticipation, CharityEvent charityEvent) {
        this.charityEventParticipation = charityEventParticipation;
        this.charityEvent = charityEvent;
    }

    public CharityEventParticipation getCharityEventParticipation() {
        return charityEventParticipation;
    }

    public void setCharityEventParticipation(CharityEventParticipation charityEventParticipation) {
        this.charityEventParticipation = charityEventParticipation;
    }

    public CharityEvent getCharityEvent() {
        return charityEvent;
    }

    public void setCharityEvent(CharityEvent charityEvent) {
        this.charityEvent = charityEvent;
    }
}
