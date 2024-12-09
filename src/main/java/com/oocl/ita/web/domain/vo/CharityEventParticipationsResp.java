package com.oocl.ita.web.domain.vo;

import com.oocl.ita.web.domain.po.CharityEventParticipation;

import java.util.List;

public class CharityEventParticipationsResp {

    private Integer id;
    private String name;
    private List<CharityEventParticipation> charityEventParticipations;

    public CharityEventParticipationsResp(Integer id, String name, List<CharityEventParticipation> charityEventParticipations) {
        this.id = id;
        this.name = name;
        this.charityEventParticipations = charityEventParticipations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CharityEventParticipation> getCharityEventParticipations() {
        return charityEventParticipations;
    }

    public void setCharityEventParticipations(List<CharityEventParticipation> charityEventParticipations) {
        this.charityEventParticipations = charityEventParticipations;
    }
}
