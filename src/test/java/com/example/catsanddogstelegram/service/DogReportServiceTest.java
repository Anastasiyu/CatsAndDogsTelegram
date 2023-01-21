package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.DogReport;
import com.example.catsanddogstelegram.repository.DogReportRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DogReportServiceTest {

    @Mock
    private DogReportRepository cogReportRepository;
    @InjectMocks
    private DogReportService catReportService;

    @Test
    void create() {
        DogReport report = new DogReport();
        report.setId(1);
        report.setChatId(123L);
        report.setText("text");
        report.setFilePath("path");

        when(cogReportRepository.findByFilePath(any(String.class)))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(report));
        when(cogReportRepository.save(report)).thenReturn(report);

        assertThat(catReportService.create(report))
                .isNotNull()
                .isEqualTo(report);
        assertThat(catReportService.create(report))
                .isNotNull()
                .isEqualTo(report);
    }

    @Test
    void readAllByDay() {
        List<DogReport> list = List.of(new DogReport());
        when(cogReportRepository.findAllByFilePathContains(any(String.class))).thenReturn(list);

        assertThat(catReportService.readAllByDay("date"))
                .isNotNull()
                .isEqualTo(list);
    }

    @Test
    void clear() {
        catReportService.clear(12345L);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(cogReportRepository).deleteAllByChatId(argumentCaptor.capture());
        Long actual = argumentCaptor.getValue();

        assertThat(actual).isEqualTo(12345L);
    }
}