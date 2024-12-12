package com.oocl.ita.web.domain.vo.charity;

import com.oocl.ita.web.domain.po.Charity.CharityEventParticipation;
import com.oocl.ita.web.domain.vo.User.UserVo;

public class UserParticipationRecResp {

    private UserVo user;
    private CharityEventParticipation charityEventParticipation;

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public CharityEventParticipation getCharityEventParticipation() {
        return charityEventParticipation;
    }

    public void setCharityEventParticipation(CharityEventParticipation charityEventParticipation) {
        this.charityEventParticipation = charityEventParticipation;
    }

    public UserParticipationRecResp(UserVo user, CharityEventParticipation charityEventParticipation) {
        this.user = user;
        this.charityEventParticipation = charityEventParticipation;
    }
}
