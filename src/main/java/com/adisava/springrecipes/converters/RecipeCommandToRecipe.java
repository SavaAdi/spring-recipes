package com.adisava.springrecipes.converters;

import com.adisava.springrecipes.commands.RecipeCommand;
import com.adisava.springrecipes.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConverter;
    private final NotesCommandToNotes notesConverter;
    private final IngredientCommandToIngredient ingredientConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter, NotesCommandToNotes notesConverter,
                                 IngredientCommandToIngredient ingredientConverter) {
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if(source == null)
        return null;

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if(source.getCategories().size() > 0 && source.getCategories() != null)
            source.getCategories()
                    .forEach(category -> recipe.getCategories()
                            .add(categoryConverter.convert(category)));

        if(source.getIngredients().size() > 0 && source.getIngredients() != null)
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients()
                            .add(ingredientConverter.convert(ingredient)));

        return recipe;
    }
}
