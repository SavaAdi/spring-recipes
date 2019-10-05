package com.adisava.springrecipes.services;

import com.adisava.springrecipes.commands.RecipeCommand;
import com.adisava.springrecipes.converters.RecipeCommandToRecipe;
import com.adisava.springrecipes.converters.RecipeToRecipeCommand;
import com.adisava.springrecipes.domain.Recipe;
import com.adisava.springrecipes.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() throws Exception{
        Recipe recipe = new Recipe();
        Long id = 1L;
        recipe.setId(id);

        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        Recipe returnedRecipe = recipeService.findById(id);
        assertNotNull("Null returned on Recipe", returnedRecipe);
        verify(recipeRepository, times(1)).findById(id);
        verify(recipeRepository, never()).findAll();

    }

    @Test
    public void getCommandByIdTest() throws Exception{
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);

        when(recipeToRecipeCommand.convert(recipe)).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findByCommandId(id);
        assertNotNull("Returned null recipe", commandById);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipeData);
        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(),1);
        verify(recipeRepository, times(1)).findAll();
    }
}