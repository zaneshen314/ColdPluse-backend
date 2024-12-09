package com.oocl.ita.web.domain.po;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String location;

    private int capacity;

    private String state;


}
