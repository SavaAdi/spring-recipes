package com.adisava.springrecipes.services;

import com.adisava.springrecipes.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
