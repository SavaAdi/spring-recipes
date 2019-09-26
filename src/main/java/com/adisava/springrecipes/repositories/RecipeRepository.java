package com.adisava.springrecipes.repositories;

import com.adisava.springrecipes.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
