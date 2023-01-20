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

    /**
     * Сохранение информации об отчете в БД
     * @param report объект с информацией об отчете
     * @return сохраненный отчет
     */
    public DogReport create(DogReport report){
        log.debug("was invoking method create");
        DogReport dogReport = dogReportRepository.findByFilePath(report.getFilePath()).orElse(null);
        if(dogReport == null){
            return dogReportRepository.save(report);
        }else{
            dogReport.setText(report.getText());
            return dogReportRepository.save(dogReport);
        }
    }

    /**
     * Поиск отчетов в БД присланный в определённый день
     * @param date дата для поиска
     * @return список отчетов
     */
    public List<DogReport> readAllByDay(String date){
        log.debug("was invoking method readAllByDay");
        return dogReportRepository.findAllByFilePathContains(date);
    }

    /**
     * Удаление отчетов определенного усыновителя
     * @param chatId id усыновителя
     */
    public void clear(long chatId){
        log.debug("was invoking method clear");
        dogReportRepository.deleteAllByChatId(chatId);
    }

    /**
     * Чтение файла изображения по пути из отчета в БД
     * @param id id отчета
     * @return массив байт изображения
     * @throws IOException - не найден файл
     */
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
