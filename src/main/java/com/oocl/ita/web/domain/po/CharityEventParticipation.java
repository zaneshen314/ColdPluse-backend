package com.oocl.ita.web.domain.po;

import javax.persistence.Id;

public class CharityEventParticipation {

    @Id
    private Integer userId;

    @Id
    private Integer charityEventId;

    private boolean enrolled;

    private boolean finished;

    private boolean closed;

    private boolean claimPoint;

}
