package com.oocl.ita.web.domain.vo;

public class UserVo {

    private Integer id;
    private String name;
    private String email;
    private Integer cumulatedPoint;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCumulatedPoint() {
        return cumulatedPoint;
    }

    public void setCumulatedPoint(Integer cumulatedPoint) {
        this.cumulatedPoint = cumulatedPoint;
    }

    public UserVo(Integer id, String name, String email, Integer cumulatedPoint) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cumulatedPoint = cumulatedPoint;
    }
}
