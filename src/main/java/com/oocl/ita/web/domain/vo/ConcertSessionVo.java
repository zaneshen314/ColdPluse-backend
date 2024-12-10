package com.oocl.ita.web.domain.vo;

import lombok.Data;

@Data
public class ConcertSessionVo {
    private Integer id;
    private String name;
    private Double maxPrice;
    private Double minPrice;
    private String start_time;
    private String venue;
    private Long duration;
}
