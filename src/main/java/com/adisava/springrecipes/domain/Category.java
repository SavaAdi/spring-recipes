package com.adisava.springrecipes.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
