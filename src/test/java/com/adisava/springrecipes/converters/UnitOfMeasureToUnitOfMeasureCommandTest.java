package com.adisava.springrecipes.converters;

import com.adisava.springrecipes.commands.UnitOfMeasureCommand;
import com.adisava.springrecipes.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "Description";
    public static final Long LONG_VALUE = 1L;
    UnitOfMeasureToUnitOfMeasureCommand converter;


    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }


    @Test
    public void testNullParamter() throws Exception {
        assertNull(converter.convert(null));
    }


    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }


    @Test
    public void convert() throws Exception {

        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);

        //when
        UnitOfMeasureCommand command = converter.convert(uom);

        //then
        assertNotNull(command);
        assertEquals(LONG_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}