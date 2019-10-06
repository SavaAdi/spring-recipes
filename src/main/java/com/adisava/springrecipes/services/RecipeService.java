package com.adisava.springrecipes.services;

import com.adisava.springrecipes.commands.RecipeCommand;
import com.adisava.springrecipes.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand findByCommandId(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    void deleteById(Long id);
}
