package com.oocl.ita.web.domain.bo;

public class ConcertScheduleRegBody {

    private Integer concertId;

    private String startTime;

    private Long duration;

    private String saleStartTime;

    public ConcertScheduleRegBody() {
    }

    public ConcertScheduleRegBody(Integer concertId, String startTime, Long duration) {
        this.concertId = concertId;
        this.startTime = startTime;
        this.duration = duration;
        this.saleStartTime = saleStartTime;
    }


    public Integer getConcertId() {
        return concertId;
    }

    public void setConcertId(Integer concertId) {
        this.concertId = concertId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
