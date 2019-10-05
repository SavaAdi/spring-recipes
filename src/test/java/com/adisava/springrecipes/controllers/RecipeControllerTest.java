package com.adisava.springrecipes.controllers;

import com.adisava.springrecipes.commands.RecipeCommand;
import com.adisava.springrecipes.domain.Recipe;
import com.adisava.springrecipes.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void getById() throws Exception {
//      given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
//        when
        when(recipeService.findById(anyLong())).thenReturn(recipe);
//        then
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {

        mockMvc.perform((get("/recipe/new")))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists(("recipe")));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
//        given
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

//        when
        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

//        then
        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "this is a string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testGetUpdateView() throws Exception{
//        given
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

//        when
        when(recipeService.findByCommandId(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }
}