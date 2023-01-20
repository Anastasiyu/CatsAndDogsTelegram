package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.constants.MenuTexts;
import com.example.catsanddogstelegram.exception.UserNotFoundException;
import com.example.catsanddogstelegram.record.CatAdopterRecord;
import com.example.catsanddogstelegram.record.DogAdopterRecord;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TelegramMessageServiceTest {
    @Mock
    private TelegramBot telegramBot;

    @Mock
    private UserService userService;
    @Mock
    private CatAdopterService catAdopterService;
    @Mock
    private DogAdopterService dogAdopterService;

    @InjectMocks
    private TelegramMessageService telegramMessageService;

    public TelegramMessageServiceTest() {
    }

    @Test
    public void startCommandReceivedTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "/start");
        telegramMessageService.startCommandReceived(update.message().from().id(), update.message().from().firstName());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Привет " + update.message().from().firstName() + ", рад помочь Вам!" +
                        " Для начала выберите приют к которому хотите обратиться.\n" +
                        "/dog - приют для собак\n" +
                        "/cat - приют для кошек");
    }

    @Test
    void ShelterCommandReceivedTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "/dog");
        telegramMessageService.ShelterCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("/aboutUs - узнать больше о приюте\n" +
                        "/adopt - как взять питомца\n" +
                        "/report - отправить отчет\n" +
                        "/volunteer - позвать волонтера");
    }

    @Test
    void aboutUsCommandReceivedTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "/aboutUs");
        telegramMessageService.aboutUsCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(MenuTexts.ABOUT_US_TEXT.getMessage());
    }


    @Test
    void adoptCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "/adopt");
        when(userService.getShelterType(update.message().from().id())).thenReturn(1);
        telegramMessageService.adoptCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(MenuTexts.ADOPT_TEXT_DOG.getMessage());
    }

    @Test
    void adoptCommandReceivedCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "/adopt");
        when(userService.getShelterType(update.message().from().id())).thenReturn(2);
        telegramMessageService.adoptCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(MenuTexts.ADOPT_TEXT_CAT.getMessage());
    }

    @Test
    void reportCommandReceivedCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "/report");

        CatAdopterRecord catAdopterRecord = new CatAdopterRecord();
        DogAdopterRecord dogAdopterRecord = new DogAdopterRecord();

        when(userService.getShelterType(update.message().from().id()))
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(2)
                .thenReturn(2);
        when(dogAdopterService.readDogAdopter(update.message().from().id()))
                .thenReturn(dogAdopterRecord)
                .thenThrow(UserNotFoundException.class);
        when(catAdopterService.readCatAdopter(update.message().from().id()))
                .thenReturn(catAdopterRecord)
                .thenThrow(UserNotFoundException.class);

        telegramMessageService.reportCommandReceived(update.message().from().id());
        telegramMessageService.reportCommandReceived(update.message().from().id());
        telegramMessageService.reportCommandReceived(update.message().from().id());
        telegramMessageService.reportCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, times(6)).execute(argumentCaptor.capture());
        List<SendMessage> actual = argumentCaptor.getAllValues();

        Assertions.assertThat(actual.get(0).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(0).getParameters().get("text"))
                .isEqualTo("Отчет должен содержать фото животного," +
                        " а так же информацию о его рационе," +
                        " общем самочувствии и привыкании к новому месту," +
                        " об изменениях в поведении (отказ от старых привычек, приобретение новых)");

        Assertions.assertThat(actual.get(1).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(1).getParameters().get("text"))
                .isEqualTo("Отправьте фотографию с прикрепленным к ней текстом");
        Assertions.assertThat(actual.get(1).getParameters().get("reply_markup"))
                .isEqualTo(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("отмена")
                                .callbackData("report cancel")));

        Assertions.assertThat(actual.get(2).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(2).getParameters().get("text"))
                .isEqualTo("у вас нет питомца");

        Assertions.assertThat(actual.get(3).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(3).getParameters().get("text"))
                .isEqualTo("Отчет должен содержать фото животного," +
                        " а так же информацию о его рационе," +
                        " общем самочувствии и привыкании к новому месту," +
                        " об изменениях в поведении (отказ от старых привычек, приобретение новых)");

        Assertions.assertThat(actual.get(4).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(4).getParameters().get("text"))
                .isEqualTo("Отправьте фотографию с прикрепленным к ней текстом");
        Assertions.assertThat(actual.get(4).getParameters().get("reply_markup"))
                .isEqualTo(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("отмена")
                                .callbackData("report cancel")));

        Assertions.assertThat(actual.get(5).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(5).getParameters().get("text"))
                .isEqualTo("у вас нет питомца");
    }

    @Test
    void registerCommandReceivedTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "/register");
        telegramMessageService.registerCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Введите ваш номер телефона");
        Assertions.assertThat(actual.getParameters().get("reply_markup"))
                .isEqualTo(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("отмена")
                                .callbackData("/cancel")));
    }

    @Test
    void registerVerifyFalseTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "AAA122334");
        telegramMessageService.registerVerify(update.message().from().id(), update.message().text());
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Неверный формат номера, попробуйте ещё раз");

    }

    @Test
    void registerVerifyTrueTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "+71112223344");
        telegramMessageService.registerVerify(update.message().from().id(), update.message().text());
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Сохранено, скоро свяжемся");

    }

    @Test
    void cancelCommandReceivedTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "/cancel");
        telegramMessageService.cancelCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("В другой раз");
    }

    @Test
    void defaultCommandReceivedTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "/default");
        telegramMessageService.defaultCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(MenuTexts.DEFAULT_TEXT.getMessage());
    }

    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%command%", replaced), Update.class);
    }
}