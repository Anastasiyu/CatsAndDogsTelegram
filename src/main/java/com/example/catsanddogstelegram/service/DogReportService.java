package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.DogReport;
import com.example.catsanddogstelegram.exception.ReportNotFoundException;
import com.example.catsanddogstelegram.repository.DogReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Slf4j
public class DogReportService {
    private final DogReportRepository dogReportRepository;

    public DogReportService(DogReportRepository dogReportRepository) {
        this.dogReportRepository = dogReportRepository;
    }

    public DogReport create(DogReport report){
        DogReport dogReport = dogReportRepository.findByFilePath(report.getFilePath()).orElse(null);
        if(dogReport == null){
            return dogReportRepository.save(report);
        }else{
            dogReport.setText(report.getText());
            return dogReportRepository.save(dogReport);
        }
    }

    public List<DogReport> readAllByDay(String date){
        return dogReportRepository.findAllByFilePathContains(date);
    }

    public void clear(long chatId){
        dogReportRepository.deleteAllByChatId(chatId);
    }

    public byte[] readFromFile(long id) throws IOException {
        log.debug("was invoking method readFromFile");
        DogReport report = dogReportRepository.findById(id).orElseThrow(() -> {
            log.error("There is not report with id = " + id);
            throw new ReportNotFoundException();
        });
        Path path = Path.of(report.getFilePath());
        return Files.readAllBytes(path);
    }
}
