package com.oocl.ita.web.domain.vo.Concert;


import com.oocl.ita.web.domain.po.Concert.Venue;
import lombok.Data;

@Data
public class ConcertVo {

    private Integer id;
    private String name;
    private Venue venue;

}
