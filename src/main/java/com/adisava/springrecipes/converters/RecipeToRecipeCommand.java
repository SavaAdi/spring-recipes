package com.adisava.springrecipes.converters;

import com.adisava.springrecipes.commands.RecipeCommand;
import com.adisava.springrecipes.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryConverter;
    private final NotesToNotesCommand notesConverter;
    private final IngredientToIngredientCommand ingredientConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter, NotesToNotesCommand notesConverter,
                                 IngredientToIngredientCommand ingredientConverter) {
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if(source == null)
            return null;

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setNotes(notesConverter.convert(source.getNotes()));

        if(source.getCategories().size() > 0 && source.getCategories() != null)
            source.getCategories()
                    .forEach(category -> recipeCommand.getCategories()
                            .add(categoryConverter.convert(category)));

        if(source.getIngredients().size() > 0 && source.getIngredients() != null)
            source.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients()
                            .add(ingredientConverter.convert(ingredient)));

        return recipeCommand;
    }
}

