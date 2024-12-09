package com.oocl.ita.web.domain.bo;

public class CharityEventRegBody {

    private Integer userId;
    private Integer charityEventId;
    private boolean claimPoint;

    public CharityEventRegBody(Integer userId, Integer charityEventId, boolean claimPoint) {
        this.userId = userId;
        this.charityEventId = charityEventId;
        this.claimPoint = claimPoint;
    }

    public CharityEventRegBody() {
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

    public boolean isClaimPoint() {
        return claimPoint;
    }

    public void setClaimPoint(boolean claimPoint) {
        this.claimPoint = claimPoint;
    }
}
