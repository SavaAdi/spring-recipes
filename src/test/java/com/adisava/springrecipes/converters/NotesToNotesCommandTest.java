package com.adisava.springrecipes.converters;

import com.adisava.springrecipes.commands.NotesCommand;
import com.adisava.springrecipes.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Recipe Notes";
    NotesToNotesCommand converter;


    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesCommand();
    }


    @Test
    public void testNullParameter() throws Exception {
        assertNull(converter.convert(null));
    }


    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Notes()));
    }


    @Test
    public void convert() throws Exception {

        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        NotesCommand command = converter.convert(notes);

        //then
        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(RECIPE_NOTES, command.getRecipeNotes());
    }
}