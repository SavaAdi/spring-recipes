package com.adisava.springrecipes.controllers;

import com.adisava.springrecipes.commands.RecipeCommand;
import com.adisava.springrecipes.services.ImageService;
import com.adisava.springrecipes.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController imageController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    public void showUploadForm() throws Exception{
//        given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

//        when
        when(recipeService.findByCommandId(anyLong())).thenReturn(command);
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService, times(1)).findByCommandId(anyLong());
    }

    @Test
    public void imagePost() throws Exception{
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("imagefile", "testingpurpose.txt",
                        "text/plain", "AdiSava".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    public void getImageFromDb() throws Exception{
//        given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        String s = "Just a string used for testing";
        Byte[] bytes = new Byte[s.getBytes().length];

        int i = 0;

        for(byte primByte : s.getBytes())
            bytes[i++] = primByte;

        command.setImage(bytes);

//        when
        when(recipeService.findByCommandId(anyLong())).thenReturn(command);
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, responseBytes.length);

    }

    @Test
    public void getImageNumberFormatException() throws Exception{

        mockMvc.perform(get("/recipe/1sdfsd/recipeimage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}