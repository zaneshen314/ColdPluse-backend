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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String state;


}
