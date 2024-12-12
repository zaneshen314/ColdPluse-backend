package com.oocl.ita.web.domain.po.Charity;

import lombok.Data;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class CharityEventParticipationKey implements Serializable {

    private Integer userId;
    private Integer charityEventId;

    public CharityEventParticipationKey() {}

    public CharityEventParticipationKey(Integer userId, Integer charityEventId) {
        this.userId = userId;
        this.charityEventId = charityEventId;
    }

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