package com.oocl.ita.web.domain.vo;


import com.oocl.ita.web.domain.po.Venue;
import lombok.Data;

@Data
public class ConcertVo {

    private Integer id;
    private String name;
    private Venue venue;

}
