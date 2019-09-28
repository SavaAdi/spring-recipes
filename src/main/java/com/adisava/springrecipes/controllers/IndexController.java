package com.adisava.springrecipes.controllers;

import com.adisava.springrecipes.domain.Category;
import com.adisava.springrecipes.domain.UnitOfMeasure;
import com.adisava.springrecipes.repositories.CategoryRepository;
import com.adisava.springrecipes.repositories.UnitOfMeasureRepository;
import com.adisava.springrecipes.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndex(Model model ){
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
