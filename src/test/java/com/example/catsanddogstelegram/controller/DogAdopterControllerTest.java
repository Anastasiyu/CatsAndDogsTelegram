package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.DogAdopter;
import com.example.catsanddogstelegram.record.DogAdopterRecord;
import com.example.catsanddogstelegram.repository.DogAdoptersRepository;
import com.example.catsanddogstelegram.service.DogAdopterService;
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


@WebMvcTest(DogAdopterController.class)
class DogAdopterControllerTest {

    @MockBean
    private DogAdopterService adopterService;

    @MockBean
    private DogAdoptersRepository adoptersRepository;

    @Autowired
    private MockMvc mockMvc;

    private final static ObjectMapper mapper = new ObjectMapper();


    @Test
    void createDogAdopterTest() throws Exception {
        DogAdopterRecord record = new DogAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setDogId(123);

        String json = mapper.writeValueAsString(record);
        when(adopterService.createDogAdopter(record)).thenReturn(record);

        mockMvc.perform(post("/dog/adopter").contentType(MediaType.APPLICATION_JSON_VALUE).content(json)
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.chatId", Matchers.equalTo(167)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Анатолий Петров")))
                .andExpect(jsonPath("$.age", Matchers.equalTo(35)))
                .andExpect(jsonPath("$.registerTime", Matchers.equalTo("2022-12-12T09:11:33.000+00:00")))
                .andExpect(jsonPath("$.email", Matchers.equalTo("adopter@mail.ru")))
                .andExpect(jsonPath("$.proneNumber", Matchers.equalTo("+79111112233")))
                .andExpect(jsonPath("$.address", Matchers.equalTo("Тамбовская 23")))
                .andExpect(jsonPath("$.dogId", Matchers.equalTo(123)));
    }

    @Test
    void readDogAdopterTest() throws Exception {
        DogAdopterRecord record = new DogAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setDogId(123);

        when(adopterService.readDogAdopter(record.getChatId())).thenReturn(record);

        mockMvc.perform(MockMvcRequestBuilders.get("/dog/adopter/" + record.getChatId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId", Matchers.equalTo(167)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Анатолий Петров")))
                .andExpect(jsonPath("$.age", Matchers.equalTo(35)))
                .andExpect(jsonPath("$.registerTime", Matchers.equalTo("2022-12-12T09:11:33.000+00:00")))
                .andExpect(jsonPath("$.email", Matchers.equalTo("adopter@mail.ru")))
                .andExpect(jsonPath("$.proneNumber", Matchers.equalTo("+79111112233")))
                .andExpect(jsonPath("$.address", Matchers.equalTo("Тамбовская 23")))
                .andExpect(jsonPath("$.dogId", Matchers.equalTo(123)));
    }

    @Test
    void updateDogAdopterTest() throws Exception {
        DogAdopterRecord record = new DogAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setDogId(123);

        String json = mapper.writeValueAsString(record);

        when(adopterService.updateDogAdopter(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/dog/adopter")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId", Matchers.equalTo(167)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Анатолий Петров")))
                .andExpect(jsonPath("$.age", Matchers.equalTo(35)))
                .andExpect(jsonPath("$.registerTime", Matchers.equalTo("2022-12-12T09:11:33.000+00:00")))
                .andExpect(jsonPath("$.email", Matchers.equalTo("adopter@mail.ru")))
                .andExpect(jsonPath("$.proneNumber", Matchers.equalTo("+79111112233")))
                .andExpect(jsonPath("$.address", Matchers.equalTo("Тамбовская 23")))
                .andExpect(jsonPath("$.dogId", Matchers.equalTo(123)));
    }

    @Test
    void setPassStatusNotFoundTest() throws Exception {
        DogAdopterRecord record = new DogAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setDogId(123);

        String json = mapper.writeValueAsString(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/dog/adopter" + record.getChatId() + "/pass")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    void addDaysToEndNotFoundTest() throws Exception {
        DogAdopterRecord record = new DogAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setDogId(123);

        doNothing().when(adoptersRepository).addDays(167, 15);

        String json = mapper.writeValueAsString(record);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/dog/adopter" + record.getChatId() + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteDogAdopter() throws Exception {
        DogAdopterRecord record = new DogAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setDogId(123);

        DogAdopter adopter = new DogAdopter();
        adopter.setChatId(167L);
        adopter.setName("Анатолий Петров");
        adopter.setAge(35);
        adopter.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        adopter.setEmail("adopter@mail.ru");
        adopter.setProneNumber("+79111112233");
        adopter.setAddress("Тамбовская 23");

        when(adoptersRepository.findById(adopter.getChatId())).thenReturn(Optional.of(adopter));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/dog/adopter/" + record.getChatId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}