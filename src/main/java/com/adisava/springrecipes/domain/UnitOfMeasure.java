package com.adisava.springrecipes.domain;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
}
