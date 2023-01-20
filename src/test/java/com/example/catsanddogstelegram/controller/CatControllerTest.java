package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.Cat;
import com.example.catsanddogstelegram.repository.CatRepository;
import com.example.catsanddogstelegram.service.CatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.Optional;

import static org.mockito.Mockito.*;

@WebMvcTest(CatController.class)
class CatControllerTest {

    @MockBean
    private CatService catService;

    @MockBean
    private CatRepository catRepository;

    @Autowired
    private MockMvc mockMvc;

    private final static ObjectMapper mapper = new ObjectMapper();

    @Test
    void createTest() throws Exception {
        Cat tempCat = new Cat();
        tempCat.setAnimalId(12);
        tempCat.setRegisterDate(Timestamp.valueOf("2022-12-12 12:11:33"));
        tempCat.setIsMale(true);
        tempCat.setAnimalName("Kitty");
        tempCat.setAnimalAge(1);
        tempCat.setDescription("Веселый и озорной кот");

        String json = mapper.writeValueAsString(tempCat);

        when(catService.createCat(tempCat)).thenReturn(tempCat);
        mockMvc.perform(post("/cat").contentType(MediaType.APPLICATION_JSON_VALUE).content(json)
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId", Matchers.equalTo(tempCat.getAnimalId())))
                .andExpect(jsonPath("$.registerDate", Matchers.equalTo("2022-12-12T09:11:33.000+00:00")))
                .andExpect(jsonPath("$.isMale", Matchers.equalTo(tempCat.getIsMale())))
                .andExpect(jsonPath("$.animalName", Matchers.equalTo(tempCat.getAnimalName())))
                .andExpect(jsonPath("$.animalAge", Matchers.equalTo(tempCat.getAnimalAge())))
                .andExpect(jsonPath("$.description", Matchers.equalTo(tempCat.getDescription())));


    }

    @Test
    void readTest() throws Exception {
        Cat tempCat = new Cat();
        tempCat.setAnimalId(12);
        tempCat.setRegisterDate(Timestamp.valueOf("2022-12-12 12:11:33"));
        tempCat.setIsMale(true);
        tempCat.setAnimalName("Kitty");
        tempCat.setAnimalAge(1);
        tempCat.setDescription("Веселый и озорной кот");


       when(catService.readCat(tempCat.getAnimalId())).thenReturn(tempCat);

        mockMvc.perform(MockMvcRequestBuilders.get("/cat/" + tempCat.getAnimalId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId", Matchers.equalTo(tempCat.getAnimalId())))
                .andExpect(jsonPath("$.registerDate", Matchers.equalTo("2022-12-12T09:11:33.000+00:00")))
                .andExpect(jsonPath("$.isMale", Matchers.equalTo(tempCat.getIsMale())))
                .andExpect(jsonPath("$.animalName", Matchers.equalTo(tempCat.getAnimalName())))
                .andExpect(jsonPath("$.animalAge", Matchers.equalTo(tempCat.getAnimalAge())))
                .andExpect(jsonPath("$.description", Matchers.equalTo(tempCat.getDescription())));
    }

    @Test
    void updateTest() throws Exception {
        Cat tempCat = new Cat();
        tempCat.setAnimalId(12);
        tempCat.setRegisterDate(Timestamp.valueOf("2022-12-12 12:11:33"));
        tempCat.setIsMale(true);
        tempCat.setAnimalName("Kitty");
        tempCat.setAnimalAge(1);
        tempCat.setDescription("Веселый и озорной кот");

        String json = mapper.writeValueAsString(tempCat);

        when(catService.updateCat(tempCat)).thenReturn(tempCat);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/cat")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId", Matchers.equalTo(tempCat.getAnimalId())))
                .andExpect(jsonPath("$.registerDate", Matchers.equalTo("2022-12-12T09:11:33.000+00:00")))
                .andExpect(jsonPath("$.isMale", Matchers.equalTo(tempCat.getIsMale())))
                .andExpect(jsonPath("$.animalName", Matchers.equalTo(tempCat.getAnimalName())))
                .andExpect(jsonPath("$.animalAge", Matchers.equalTo(tempCat.getAnimalAge())))
                .andExpect(jsonPath("$.description", Matchers.equalTo(tempCat.getDescription())));

    }

    @Test
    void deleteTest() throws Exception {
        Cat tempCat = new Cat();
        tempCat.setAnimalId(12);
        tempCat.setRegisterDate(Timestamp.valueOf("2022-12-12 12:11:33"));
        tempCat.setIsMale(true);
        tempCat.setAnimalName("Kitty");
        tempCat.setAnimalAge(1);
        tempCat.setDescription("Веселый и озорной кот");

        when(catRepository.findById(tempCat.getAnimalId())).thenReturn(Optional.of(tempCat));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cat/" + tempCat.getAnimalId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}