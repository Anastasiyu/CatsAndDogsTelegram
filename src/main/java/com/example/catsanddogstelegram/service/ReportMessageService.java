package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.CatReport;
import com.example.catsanddogstelegram.entity.DogReport;
import com.example.catsanddogstelegram.repository.VolunteerRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class ReportMessageService {
    private final TelegramBot telegramBot;
    private final UserService userService;
    private final CatAdopterService catAdopterService;
    private final DogAdopterService dogAdopterService;
    private final DogReportService dogReportService;
    private final CatReportService catReportService;
    private final VolunteerRepository volunteerRepository;

    @Value("${path.to.dog.folder}")
    private String dogDir;
    @Value("${path.to.cat.folder}")
    private String catDir;

    public ReportMessageService(TelegramBot telegramBot, UserService userService,
                                CatAdopterService catAdopterService, DogAdopterService dogAdopterService,
                                DogReportService dogReportService, CatReportService catReportService,
                                VolunteerRepository volunteerRepository) {
        this.telegramBot = telegramBot;
        this.userService = userService;
        this.catAdopterService = catAdopterService;
        this.dogAdopterService = dogAdopterService;
        this.dogReportService = dogReportService;
        this.catReportService = catReportService;
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Обработка update при статусе запроса отчета true у усыновитя
     * @param update изформация о сообщении
     * @param chatId id чата из которого пришел update
     */
    public void reportUpdateListener(Update update, long chatId) {
        log.debug("method reportUpdateListener started");
        if (update.message().photo() == null || update.message().caption() == null) {
            SendMessage smg = new SendMessage(chatId, "Отправьте фотографию с прикрепленным к ней текстом")
                    .replyMarkup(new InlineKeyboardMarkup(
                            new InlineKeyboardButton("отмена")
                                    .callbackData("report cancel")));
            telegramBot.execute(smg);
            return;
        }
        if (userService.getShelterType(chatId) == 1) {
            DogReport report = new DogReport();
            report.setChatId(chatId);
            report.setFilePath(savePhoto(update.message().photo()[2].fileId(), dogDir, chatId));
            report.setText(update.message().caption());
            dogReportService.create(report);
            dogAdopterService.setStatus(chatId, false, Timestamp.valueOf(LocalDateTime.now()));
        } else {
            CatReport report = new CatReport();
            report.setChatId(chatId);
            report.setFilePath(savePhoto(update.message().photo()[2].fileId(), catDir, chatId));
            report.setText(update.message().caption());
            catReportService.create(report);
            catAdopterService.setStatus(chatId, false, Timestamp.valueOf(LocalDateTime.now()));
        }
        SendMessage smg = new SendMessage(chatId, "На сегодня все");
        telegramBot.execute(smg);
    }

    /**
     * Сохранение присланного боту изображения на сервер
     * @param fileId id изображения на сервере telegram
     * @param dir директория для сохранения файлов
     * @param chatId id чата из которого пришел update
     * @return путь к файлу на сервере
     */
    public String savePhoto(String fileId, String dir, long chatId) {
        log.debug("method savePhoto started");
        GetFile getFile = new GetFile(fileId);
        String url = telegramBot.getFullFilePath(telegramBot.execute(getFile).file());

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Path path = Path.of(dir,
                chatId + "_" + LocalDateTime.now().format(format) + ".jpg");

        try (InputStream is = new URL(url).openStream()) {
            byte[] img = is.readAllBytes();
            Files.createDirectories(path.getParent());
            Files.write(path, img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path.toString();
    }

    /**
     * Установление статуса запроса отчета на false
     * @param chatId id чата из которого пришел update
     */
    public void cancelCommandReceived(long chatId) {
        log.debug("method cancelCommandReceived started");
        if (userService.getShelterType(chatId) == 1) {
            dogAdopterService.setStatus(chatId, false);
        } else {
            catAdopterService.setStatus(chatId, false);
        }
        SendMessage smg = new SendMessage(chatId, "В другой раз");
        telegramBot.execute(smg);
    }

    /**
     * Отправка стандартного сообщения при некорректном заполнении отчета
     * @param chatId id чата из которого пришел update
     */
    public void sendDefaultMessage(long chatId) {
        log.debug("method sendDefaultMessage started");
        SendMessage smg = new SendMessage(chatId,
                "Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо." +
                        " Пожалуйста, подойди ответственнее к этому занятию. В противном случае" +
                        " волонтеры приюта будут обязаны самолично проверять условия содержания животного");
        telegramBot.execute(smg);
    }

    /**
     * Запланированная проверка последнего времени отчета усыновителей.
     * Проверка каждый день в 21:00 по времени сервера
     */
    @Scheduled(cron = "0 0 21 * * *")
    public void checker(){
        log.info("scheduler started");
        List<Long> expired = dogAdopterService.findAllExpired();
        expired.addAll(catAdopterService.findAllExpired());

        expired.forEach(id -> {
            SendMessage smg = new SendMessage(id, "Отправьте отчет как можно скорее");
            telegramBot.execute(smg);
        });

        expired = dogAdopterService.findAllExpiredTooMuch();
        expired.addAll(catAdopterService.findAllExpiredTooMuch());

        if(!expired.isEmpty()){
            List<Long> volunteers = volunteerRepository.findAllByOnline(true);
            expired.forEach(id ->
                volunteers.forEach(vid -> {
                    SendMessage request = new SendMessage(vid, "Нет отчета более 2 дней")
                            .replyMarkup(new InlineKeyboardMarkup(
                                    new InlineKeyboardButton("help").url("tg://user?id=" + id)
                            ));
                    telegramBot.execute(request);
                }));
        }
    }
}
