package com.adisava.springrecipes.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    Category category;

    @Before
    public void setUp(){
        category = new Category();
    }

    @Test
    public void getDescription() {
        String descriptionValue = "American";
        category.setDescription(descriptionValue);
        assertEquals(descriptionValue, category.getDescription());
    }

    @Test
    public void getRecipes() {
    }

    @Test
    public void setId() {
    }
}