package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.CatReport;
import com.example.catsanddogstelegram.exception.ReportNotFoundException;
import com.example.catsanddogstelegram.repository.CatReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatReportServiceTest {
    @Mock
    private CatReportRepository catReportRepository;
    @InjectMocks
    private CatReportService catReportService;

    @Test
    void create() {
        CatReport report = new CatReport();
        report.setId(1);
        report.setChatId(123L);
        report.setText("text");
        report.setFilePath("path");

        when(catReportRepository.findByFilePath(any(String.class)))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(report));
        when(catReportRepository.save(report)).thenReturn(report);

        assertThat(catReportService.create(report))
                .isNotNull()
                .isEqualTo(report);
        assertThat(catReportService.create(report))
                .isNotNull()
                .isEqualTo(report);
    }

    @Test
    void readAllByDay() {
        List<CatReport> list = List.of(new CatReport());
        when(catReportRepository.findAllByFilePathContains(any(String.class))).thenReturn(list);

        assertThat(catReportService.readAllByDay("date"))
                .isNotNull()
                .isEqualTo(list);
    }

    @Test
    void clear() {
        catReportService.clear(12345L);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(catReportRepository).deleteAllByChatId(argumentCaptor.capture());
        Long actual = argumentCaptor.getValue();

        assertThat(actual).isEqualTo(12345L);
    }

    @Test
    void readFromFileTest() {
        CatReport report = new CatReport();
        report.setFilePath("path");
        when(catReportRepository.findById(any()))
                .thenThrow(ReportNotFoundException.class);

        assertThatExceptionOfType(ReportNotFoundException.class).isThrownBy(() -> catReportService.readFromFile(123L));
    }
}