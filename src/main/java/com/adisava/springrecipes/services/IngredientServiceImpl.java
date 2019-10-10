package com.adisava.springrecipes.services;

import com.adisava.springrecipes.commands.IngredientCommand;
import com.adisava.springrecipes.converters.IngredientCommandToIngredient;
import com.adisava.springrecipes.converters.IngredientToIngredientCommand;
import com.adisava.springrecipes.domain.Ingredient;
import com.adisava.springrecipes.domain.Recipe;
import com.adisava.springrecipes.repositories.RecipeRepository;
import com.adisava.springrecipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository
                                ,IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if(!optionalRecipe.isPresent()){
            // TODO: 06.10.2019 Handling error 
            log.error("Recipe not found for the id: " + recipeId);
        }

        Recipe recipe = optionalRecipe.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            // TODO: 06.10.2019 Handling error
            log.error("Ingredient not found for the id: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command){
        Optional<Recipe> optionalRecipe = recipeRepository.findById(command.getRecipeId());

        if(!optionalRecipe.isPresent()){
            // TODO: 07.10.2019 Error Handling 
            log.debug("Recipe not found for the id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient foundIngredient = ingredientOptional.get();
                foundIngredient.setDescription(command.getDescription());
                foundIngredient.setAmount(command.getAmount());
                foundIngredient.setUom(unitOfMeasureRepository
                                                .findById(command.getUom().getId())
                                                .orElseThrow(() -> new RuntimeException("Unit of Measure not found")));
            } else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngr -> recipeIngr.getId().equals(command.getId()))
                    .findFirst();

//            check if that ingredient already exists
            if(!savedIngredientOptional.isPresent()){
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngr -> recipeIngr.getDescription().equals(command.getDescription()))
                        .filter(recipeIngr -> recipeIngr.getAmount().equals(command.getAmount()))
                        .filter(recipeIngr -> recipeIngr.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }

    @Override
    public void deleteById(Long recipeId, Long ingredientId){
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if(optionalRecipe.isPresent()){
            Recipe recipe = optionalRecipe.get();
            log.debug("Recipe with id: " + recipeId+ " found");

            Optional<Ingredient> optionalIngredient = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();
            if(optionalIngredient.isPresent()){
                log.debug("Ingredient found with id: " + ingredientId);
                Ingredient deletedIngredient = optionalIngredient.get();
                deletedIngredient.setRecipe(null);
                recipe.getIngredients().remove(deletedIngredient);
                recipeRepository.save(recipe);
            }
        }
        else{
            log.debug("No recipe found with id: "+ recipeId);
        }
    }
}
