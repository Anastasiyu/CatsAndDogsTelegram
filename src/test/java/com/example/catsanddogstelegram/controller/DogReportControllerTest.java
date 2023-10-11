package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.DogReport;
import com.example.catsanddogstelegram.repository.DogReportRepository;
import com.example.catsanddogstelegram.service.DogReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import org.hamcrest.Matchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DogReportController.class)
class DogReportControllerTest {

    @MockBean
    private DogReportService reportService;

    @MockBean
    private DogReportRepository reportRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void findAllByDateTest() throws Exception {
        DogReport tempDogReport = new DogReport();
        tempDogReport.setId(123L);
        tempDogReport.setChatId(167L);
        tempDogReport.setText("Ежедневный отчет");
        tempDogReport.setFilePath("/report");

        List<DogReport> tempListDogReport = new ArrayList<>();
        tempListDogReport.add(tempDogReport);

        when(reportService.readAllByDay("2022-12-12")).thenReturn(tempListDogReport);

        mockMvc.perform(MockMvcRequestBuilders.get("/dog/report?date=2022-12-12")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(123)))
                .andExpect(jsonPath("$[0].chatId", Matchers.equalTo(167)))
                .andExpect(jsonPath("$[0].text", Matchers.equalTo(tempDogReport.getText())))
                .andExpect(jsonPath("$[0].filePath", Matchers.equalTo(tempDogReport.getFilePath())));
    }

    @Test
    void deleteByChatIdTest() throws Exception {
        DogReport tempDogReport = new DogReport();
        tempDogReport.setId(123L);
        tempDogReport.setChatId(167L);
        tempDogReport.setText("Ежедневный отчет");
        tempDogReport.setFilePath("/report");

        doNothing().when(reportRepository).deleteAllByChatId(tempDogReport.getChatId());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/dog/report?chatId=" + tempDogReport.getChatId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}