package com.oocl.ita.web.domain.po;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class CharityEventParticipationKey implements Serializable {

    private Integer userId;
    private Integer charityEventId;

    // Default constructor
    public CharityEventParticipationKey() {}

    // Constructor
    public CharityEventParticipationKey(Integer userId, Integer charityEventId) {
        this.userId = userId;
        this.charityEventId = charityEventId;
    }

    // Getters and setters
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

    // hashCode and equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharityEventParticipationKey that = (CharityEventParticipationKey) o;
        return Objects.equals(userId, that.userId) && Objects.equals(charityEventId, that.charityEventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, charityEventId);
    }
}