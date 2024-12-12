package com.oocl.ita.web.domain.po.Concert;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer venueId;

    private String name;

    private String ImgUrl;
}
