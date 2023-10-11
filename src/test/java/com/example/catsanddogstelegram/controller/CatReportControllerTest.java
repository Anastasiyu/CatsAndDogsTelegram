package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.CatReport;
import com.example.catsanddogstelegram.repository.CatReportRepository;
import com.example.catsanddogstelegram.service.CatReportService;
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

@WebMvcTest(CatReportController.class)
class CatReportControllerTest {

    @MockBean
    private CatReportService catReportService;

    @MockBean
    private CatReportRepository catReportRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllByDateTest() throws Exception {
        CatReport tempCatReport = new CatReport();
        tempCatReport.setId(123L);
        tempCatReport.setChatId(167L);
        tempCatReport.setText("Ежедневный отчет");
        tempCatReport.setFilePath("/report");

        List<CatReport> tempListCatReport = new ArrayList<>();
        tempListCatReport.add(tempCatReport);

        when(catReportService.readAllByDay("2022-12-12")).thenReturn(tempListCatReport);

        mockMvc.perform(MockMvcRequestBuilders.get("/cat/report?date=2022-12-12")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(123)))
                .andExpect(jsonPath("$[0].chatId", Matchers.equalTo(167)))
                .andExpect(jsonPath("$[0].text", Matchers.equalTo(tempCatReport.getText())))
                .andExpect(jsonPath("$[0].filePath", Matchers.equalTo(tempCatReport.getFilePath())));
    }

    @Test
    void deleteByChatIdTest() throws Exception {
        CatReport tempCatReport = new CatReport();
        tempCatReport.setId(123L);
        tempCatReport.setChatId(167L);
        tempCatReport.setText("Ежедневный отчет");
        tempCatReport.setFilePath("/report");

        doNothing().when(catReportRepository).deleteAllByChatId(tempCatReport.getChatId());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cat/report?chatId=" + tempCatReport.getChatId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}