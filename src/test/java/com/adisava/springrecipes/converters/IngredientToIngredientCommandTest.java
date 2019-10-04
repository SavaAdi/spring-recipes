package com.adisava.springrecipes.converters;

import com.adisava.springrecipes.commands.IngredientCommand;
import com.adisava.springrecipes.commands.UnitOfMeasureCommand;
import com.adisava.springrecipes.domain.Ingredient;
import com.adisava.springrecipes.domain.Recipe;
import com.adisava.springrecipes.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {

    public static final Recipe RECIPE = new Recipe();
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "Pizza";
    public static final Long ID_VALUE = 1L;
    public static final Long UOM_ID = 2L;
    IngredientToIngredientCommand converter;


    @Before
    public void setUp() throws Exception {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }


    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }


    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Ingredient()));
    }


    @Test
    public void convert() throws Exception {

        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        ingredient.setUom(unitOfMeasure);

        //when
        IngredientCommand command = converter.convert(ingredient);

        //then
        assertNotNull(command);
        assertNotNull(command.getUom());
        assertEquals(ID_VALUE, command.getId());
        assertEquals(AMOUNT, command.getAmount());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(UOM_ID, command.getUom().getId());
    }

    @Test
    public void convertWithNullUOM() throws Exception {

        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();

        //when
        IngredientCommand command = converter.convert(ingredient);

        //then
        assertNotNull(command);
        assertNull(command.getUom());
        assertEquals(ID_VALUE, command.getId());
        assertEquals(AMOUNT, command.getAmount());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}