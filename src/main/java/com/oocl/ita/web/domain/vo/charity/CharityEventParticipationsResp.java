package com.oocl.ita.web.domain.vo.charity;

import com.oocl.ita.web.domain.po.CharityEvent;

import java.util.List;

public class CharityEventParticipationsResp {

    private CharityEvent charityEvent;
    private List<UserParticipationRecResp> userParticipationRecResps;

    public List<UserParticipationRecResp> getUserParticipationRecResps() {
        return userParticipationRecResps;
    }

    public void setUserParticipationRecResps(List<UserParticipationRecResp> userParticipationRecResps) {
        this.userParticipationRecResps = userParticipationRecResps;
    }

    public CharityEventParticipationsResp(CharityEvent charityEvent, List<UserParticipationRecResp> userParticipationRecResps) {
        this.charityEvent = charityEvent;
        this.userParticipationRecResps = userParticipationRecResps;
    }

    public CharityEvent getCharityEvent() {
        return charityEvent;
    }

    public void setCharityEvent(CharityEvent charityEvent) {
        this.charityEvent = charityEvent;
    }
}
