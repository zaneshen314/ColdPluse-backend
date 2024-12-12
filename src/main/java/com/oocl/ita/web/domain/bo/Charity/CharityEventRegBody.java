package com.oocl.ita.web.domain.bo.Charity;

public class CharityEventRegBody {

    private Integer charityEventId;
    private boolean claimPoint;

    public CharityEventRegBody(Integer charityEventId, boolean claimPoint) {
        this.charityEventId = charityEventId;
        this.claimPoint = claimPoint;
    }

    public CharityEventRegBody() {
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
