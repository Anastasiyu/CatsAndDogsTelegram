package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.Volunteer;
import com.example.catsanddogstelegram.repository.VolunteerRepository;
import com.example.catsanddogstelegram.service.ReportMessageService;
import com.example.catsanddogstelegram.service.VolunteerService;
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

import java.util.Optional;

import static org.mockito.Mockito.*;

@WebMvcTest(VolunteerController.class)
class VolunteerControllerTest {

    @MockBean
    private VolunteerService service;

    @MockBean
    private VolunteerRepository repository;

    @MockBean
    private ReportMessageService reportMessageService;

    @Autowired
    private MockMvc mockMvc;

    private final static ObjectMapper mapper = new ObjectMapper();

    @Test
    void createVolunteerTest() throws Exception {
        Volunteer tempVolunteer = new Volunteer();
        tempVolunteer.setId(12);
        tempVolunteer.setChatId(167L);
        tempVolunteer.setName("Пётр");
        tempVolunteer.setOnline(true);

        String json = mapper.writeValueAsString(tempVolunteer);

        when(service.createVolunteer(tempVolunteer)).thenReturn(tempVolunteer);
        mockMvc.perform(post("/volunteer").contentType(MediaType.APPLICATION_JSON_VALUE).content(json)
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(tempVolunteer.getId())))
                .andExpect(jsonPath("$.chatId", Matchers.equalTo(167)))
                .andExpect(jsonPath("$.name", Matchers.equalTo(tempVolunteer.getName())))
                .andExpect(jsonPath("$.online", Matchers.equalTo(tempVolunteer.isOnline())));
    }

    @Test
    void readVolunteerTest() throws Exception {
        Volunteer tempVolunteer = new Volunteer();
        tempVolunteer.setId(12);
        tempVolunteer.setChatId(167L);
        tempVolunteer.setName("Пётр");
        tempVolunteer.setOnline(true);

        when(service.readVolunteerById(tempVolunteer.getId())).thenReturn(tempVolunteer);

        mockMvc.perform(MockMvcRequestBuilders.get("/volunteer/" + tempVolunteer.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(tempVolunteer.getId())))
                .andExpect(jsonPath("$.chatId", Matchers.equalTo(167)))
                .andExpect(jsonPath("$.name", Matchers.equalTo(tempVolunteer.getName())))
                .andExpect(jsonPath("$.online", Matchers.equalTo(tempVolunteer.isOnline())));
    }

    @Test
    void sendDefaultMsgTest() throws Exception {
        Volunteer tempVolunteer = new Volunteer();
        tempVolunteer.setId(12);
        tempVolunteer.setChatId(167L);
        tempVolunteer.setName("Пётр");
        tempVolunteer.setOnline(true);

       doNothing().when(reportMessageService).sendDefaultMessage(tempVolunteer.getChatId());

        mockMvc.perform(MockMvcRequestBuilders.get("/volunteer?chatId=" + tempVolunteer.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());

    }

    @Test
    void updateVolunteerTest() throws Exception {
        Volunteer tempVolunteer = new Volunteer();
        tempVolunteer.setId(12);
        tempVolunteer.setChatId(167L);
        tempVolunteer.setName("Пётр");
        tempVolunteer.setOnline(true);

        String json = mapper.writeValueAsString(tempVolunteer);

        when(service.updateVolunteer(tempVolunteer)).thenReturn(tempVolunteer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/volunteer/" + tempVolunteer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(mockRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(tempVolunteer.getId())))
                .andExpect(jsonPath("$.chatId", Matchers.equalTo(167)))
                .andExpect(jsonPath("$.name", Matchers.equalTo(tempVolunteer.getName())))
                .andExpect(jsonPath("$.online", Matchers.equalTo(tempVolunteer.isOnline())));
    }

    @Test
    void deleteVolunteerTest() throws Exception {
        Volunteer tempVolunteer = new Volunteer();
        tempVolunteer.setId(12);
        tempVolunteer.setChatId(167L);
        tempVolunteer.setName("Пётр");
        tempVolunteer.setOnline(true);

        when(repository.findById(tempVolunteer.getId())).thenReturn(Optional.of(tempVolunteer));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteer/" + tempVolunteer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}