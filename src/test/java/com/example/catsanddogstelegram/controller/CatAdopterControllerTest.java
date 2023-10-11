package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.CatAdopter;
import com.example.catsanddogstelegram.record.CatAdopterRecord;
import com.example.catsanddogstelegram.repository.CatAdoptersRepository;
import com.example.catsanddogstelegram.service.CatAdopterService;
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


@WebMvcTest(CatAdopterController.class)
class CatAdopterControllerTest {

    @MockBean
    private CatAdopterService adopterService;

    @MockBean
    private CatAdoptersRepository catAdoptersRepository;

    @Autowired
    private MockMvc mockMvc;

    private final static ObjectMapper mapper = new ObjectMapper();

    @Test
    void createCatAdopterTest() throws Exception {
        CatAdopterRecord record = new CatAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setCatId(123);

        String json = mapper.writeValueAsString(record);
        when(adopterService.createCatAdopter(record)).thenReturn(record);
        mockMvc.perform(post("/cat/adopter").contentType(MediaType.APPLICATION_JSON_VALUE).content(json)
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.chatId", Matchers.equalTo(167)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Анатолий Петров")))
                .andExpect(jsonPath("$.age", Matchers.equalTo(35)))
                .andExpect(jsonPath("$.registerTime", Matchers.equalTo("2022-12-12T09:11:33.000+00:00")))
                .andExpect(jsonPath("$.email", Matchers.equalTo("adopter@mail.ru")))
                .andExpect(jsonPath("$.proneNumber", Matchers.equalTo("+79111112233")))
                .andExpect(jsonPath("$.address", Matchers.equalTo("Тамбовская 23")))
                .andExpect(jsonPath("$.catId", Matchers.equalTo(123)));
    }

    @Test
    void readCatAdopterTest() throws Exception {
        CatAdopterRecord record = new CatAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setCatId(123);

        when(adopterService.readCatAdopter(record.getChatId())).thenReturn(record);
        mockMvc.perform(MockMvcRequestBuilders.get("/cat/adopter/" + record.getChatId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId", Matchers.equalTo(167)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Анатолий Петров")))
                .andExpect(jsonPath("$.age", Matchers.equalTo(35)))
                .andExpect(jsonPath("$.registerTime", Matchers.equalTo("2022-12-12T09:11:33.000+00:00")))
                .andExpect(jsonPath("$.email", Matchers.equalTo("adopter@mail.ru")))
                .andExpect(jsonPath("$.proneNumber", Matchers.equalTo("+79111112233")))
                .andExpect(jsonPath("$.address", Matchers.equalTo("Тамбовская 23")))
                .andExpect(jsonPath("$.catId", Matchers.equalTo(123)));
    }

    @Test
    void updateCatAdopterTest() throws Exception {
        CatAdopterRecord record = new CatAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setCatId(123);

        String json = mapper.writeValueAsString(record);

        when(adopterService.updateCatAdopter(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/cat/adopter")
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
                .andExpect(jsonPath("$.catId", Matchers.equalTo(123)));
    }

    @Test
    void setPassStatusFalseTest() throws Exception {
        CatAdopterRecord record = new CatAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setCatId(123);

        String json = mapper.writeValueAsString(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/cat/adopter/" + record.getChatId() + "/pass?pass=false")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    void addDaysToEndNotFoundTest() throws Exception {
        CatAdopterRecord record = new CatAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setCatId(123);

        String json = mapper.writeValueAsString(record);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/cat/adopter/" + record.getChatId() + "/add?days=15")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    void deleteCatAdopterTest() throws Exception {

        CatAdopterRecord record = new CatAdopterRecord();
        record.setChatId(167L);
        record.setName("Анатолий Петров");
        record.setAge(35);
        record.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        record.setEmail("adopter@mail.ru");
        record.setProneNumber("+79111112233");
        record.setAddress("Тамбовская 23");
        record.setCatId(123);


        CatAdopter adopter = new CatAdopter();
        adopter.setChatId(167L);
        adopter.setName("Анатолий Петров");
        adopter.setAge(35);
        adopter.setRegisterTime(Timestamp.valueOf("2022-12-12 12:11:33"));
        adopter.setEmail("adopter@mail.ru");
        adopter.setProneNumber("+79111112233");
        adopter.setAddress("Тамбовская 23");

        when(catAdoptersRepository.findById(adopter.getChatId())).thenReturn(Optional.of(adopter));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cat/adopter/" + record.getChatId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}