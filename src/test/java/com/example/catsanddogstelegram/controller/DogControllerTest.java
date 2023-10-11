package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.Dog;
import com.example.catsanddogstelegram.repository.DogRepository;
import com.example.catsanddogstelegram.service.DogService;
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

@WebMvcTest(DogController.class)
class DogControllerTest {

    @MockBean
    private DogService dogService;

    @MockBean
    private DogRepository dogRepository;

    @Autowired
    private MockMvc mockMvc;

    private final static ObjectMapper mapper = new ObjectMapper();

    @Test
    void createTest() throws Exception {
        Dog tempDog = new Dog();
        tempDog.setAnimalId(12);
        tempDog.setRegisterDate(Timestamp.valueOf("2022-12-12 12:11:33"));
        tempDog.setIsMale(true);
        tempDog.setAnimalName("Пират");
        tempDog.setAnimalAge(1);
        tempDog.setDescription("Веселый и озорной пёс");

        String json = mapper.writeValueAsString(tempDog);

        when(dogService.createDog(tempDog)).thenReturn(tempDog);
        mockMvc.perform(post("/dog").contentType(MediaType.APPLICATION_JSON_VALUE).content(json)
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId", Matchers.equalTo(tempDog.getAnimalId())))
                .andExpect(jsonPath("$.registerDate", Matchers.equalTo("2022-12-12T09:11:33.000+00:00")))
                .andExpect(jsonPath("$.isMale", Matchers.equalTo(tempDog.getIsMale())))
                .andExpect(jsonPath("$.animalName", Matchers.equalTo(tempDog.getAnimalName())))
                .andExpect(jsonPath("$.animalAge", Matchers.equalTo(tempDog.getAnimalAge())))
                .andExpect(jsonPath("$.description", Matchers.equalTo(tempDog.getDescription())));
    }

    @Test
    void readTest() throws Exception {
        Dog tempDog = new Dog();
        tempDog.setAnimalId(12);
        tempDog.setRegisterDate(Timestamp.valueOf("2022-12-12 12:11:33"));
        tempDog.setIsMale(true);
        tempDog.setAnimalName("Пират");
        tempDog.setAnimalAge(1);
        tempDog.setDescription("Веселый и озорной пёс");

        when(dogService.readDog(tempDog.getAnimalId())).thenReturn(tempDog);

        mockMvc.perform(MockMvcRequestBuilders.get("/dog/" + tempDog.getAnimalId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId", Matchers.equalTo(tempDog.getAnimalId())))
                .andExpect(jsonPath("$.registerDate", Matchers.equalTo("2022-12-12T09:11:33.000+00:00")))
                .andExpect(jsonPath("$.isMale", Matchers.equalTo(tempDog.getIsMale())))
                .andExpect(jsonPath("$.animalName", Matchers.equalTo(tempDog.getAnimalName())))
                .andExpect(jsonPath("$.animalAge", Matchers.equalTo(tempDog.getAnimalAge())))
                .andExpect(jsonPath("$.description", Matchers.equalTo(tempDog.getDescription())));
    }

    @Test
    void updateTest() throws Exception {
        Dog tempDog = new Dog();
        tempDog.setAnimalId(12);
        tempDog.setRegisterDate(Timestamp.valueOf("2022-12-12 12:11:33"));
        tempDog.setIsMale(true);
        tempDog.setAnimalName("Пират");
        tempDog.setAnimalAge(1);
        tempDog.setDescription("Веселый и озорной пёс");

        String json = mapper.writeValueAsString(tempDog);

        when(dogService.updateDog(tempDog)).thenReturn(tempDog);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/dog")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId", Matchers.equalTo(tempDog.getAnimalId())))
                .andExpect(jsonPath("$.registerDate", Matchers.equalTo("2022-12-12T09:11:33.000+00:00")))
                .andExpect(jsonPath("$.isMale", Matchers.equalTo(tempDog.getIsMale())))
                .andExpect(jsonPath("$.animalName", Matchers.equalTo(tempDog.getAnimalName())))
                .andExpect(jsonPath("$.animalAge", Matchers.equalTo(tempDog.getAnimalAge())))
                .andExpect(jsonPath("$.description", Matchers.equalTo(tempDog.getDescription())));
    }

    @Test
    void deleteTest() throws Exception {
        Dog tempDog = new Dog();
        tempDog.setAnimalId(12);
        tempDog.setRegisterDate(Timestamp.valueOf("2022-12-12 12:11:33"));
        tempDog.setIsMale(true);
        tempDog.setAnimalName("Пират");
        tempDog.setAnimalAge(1);
        tempDog.setDescription("Веселый и озорной пёс");

        when(dogRepository.findById(tempDog.getAnimalId())).thenReturn(Optional.of(tempDog));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/dog/" + tempDog.getAnimalId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}