package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.component.RecordMapper;
import com.example.catsanddogstelegram.entity.Cat;
import com.example.catsanddogstelegram.entity.CatAdopter;
import com.example.catsanddogstelegram.record.CatAdopterRecord;
import com.example.catsanddogstelegram.repository.CatAdoptersRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatAdopterServiceTest {

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private CatAdoptersRepository adoptersRepository;
    @Mock
    private RecordMapper mapper;
    @InjectMocks
    private CatAdopterService catAdopterService;

    @Test
    void createCatAdopterTest() {
        CatAdopterRecord record = new CatAdopterRecord();
        record.setChatId(12345L);
        record.setName("name");
        record.setAge(18);
        record.setEmail("mail@mail.ru");
        record.setRegisterTime(Timestamp.valueOf(LocalDateTime.now()));
        record.setProneNumber("9812938");
        record.setAddress("address");
        record.setCatId(1);

        Cat cat = new Cat();
        cat.setAnimalId(1);

        CatAdopter entity = new CatAdopter();
        entity.setChatId(12345L);
        entity.setName("name");
        entity.setAge(18);
        entity.setEmail("mail@mail.ru");
        entity.setRegisterTime(Timestamp.valueOf(LocalDateTime.now()));
        entity.setProneNumber("9812938");
        entity.setAddress("address");
        entity.setCat(cat);

        when(mapper.toEntity(record)).thenReturn(entity);
        when(mapper.toRecord(entity)).thenReturn(record);
        when(adoptersRepository.save(entity)).thenReturn(entity);

        assertThat(catAdopterService.createCatAdopter(record))
                .isNotNull()
                .isEqualToIgnoringGivenFields(record, "registerTime");
    }

    @Test
    void readCatAdopterTest() {
        CatAdopterRecord record = new CatAdopterRecord();
        record.setChatId(12345L);
        record.setName("name");
        record.setAge(18);
        record.setEmail("mail@mail.ru");
        record.setRegisterTime(Timestamp.valueOf(LocalDateTime.now()));
        record.setProneNumber("9812938");
        record.setAddress("address");
        record.setCatId(1);

        when(mapper.toRecord(any(CatAdopter.class))).thenReturn(record);
        when(adoptersRepository.findById(record.getChatId())).thenReturn(Optional.of(new CatAdopter()));

        assertThat(catAdopterService.readCatAdopter(record.getChatId()))
                .isNotNull()
                .isEqualTo(record);
    }

    @Test
    void updateCatAdopterTest() {
        CatAdopterRecord record = new CatAdopterRecord();
        record.setChatId(12345L);
        record.setName("name");
        record.setAge(18);
        record.setEmail("mail@mail.ru");
        record.setProneNumber("9812938");
        record.setAddress("address");
        record.setCatId(1);

        Cat cat = new Cat();
        cat.setAnimalId(1);

        CatAdopter entity = new CatAdopter();
        entity.setChatId(12345L);
        entity.setName("name");
        entity.setAge(18);
        entity.setEmail("mail@mail.ru");
        entity.setProneNumber("9812938");
        entity.setAddress("address");
        entity.setCat(cat);

        when(mapper.toRecord(entity)).thenReturn(record);
        when(adoptersRepository.findById(record.getChatId())).thenReturn(Optional.of(entity));
        when(mapper.toEntity(record)).thenReturn(entity);
        when(adoptersRepository.save(entity)).thenReturn(entity);

        assertThat(catAdopterService.updateCatAdopter(record))
                .isNotNull()
                .isEqualTo(record);
    }

    @Test
    void deleteCatAdopterTest() {
        Cat cat = new Cat();
        cat.setAnimalId(1);

        CatAdopter entity = new CatAdopter();
        entity.setChatId(12345L);
        entity.setName("name");
        entity.setAge(18);
        entity.setEmail("mail@mail.ru");
        entity.setProneNumber("9812938");
        entity.setAddress("address");
        entity.setCat(cat);

        when(mapper.toRecord(entity)).thenReturn(new CatAdopterRecord());
        when(mapper.toEntity(any(CatAdopterRecord.class))).thenReturn(entity);
        when(adoptersRepository.findById(entity.getChatId())).thenReturn(Optional.of(entity));
        catAdopterService.deleteCatAdopter(entity.getChatId());

        ArgumentCaptor<CatAdopter> argumentCaptor = ArgumentCaptor.forClass(CatAdopter.class);
        verify(adoptersRepository).delete(argumentCaptor.capture());
        CatAdopter actual = argumentCaptor.getValue();

        Assertions.assertThat(actual).isEqualTo(entity);
    }

    @Test
    void setPassTest() {
        catAdopterService.setPass(true, 12345L);

        ArgumentCaptor<Long> argumentCaptor1 = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> argumentCaptor2 = ArgumentCaptor.forClass(Boolean.class);
        verify(adoptersRepository).setPass(argumentCaptor2.capture(), argumentCaptor1.capture());
        Long actual1 = argumentCaptor1.getValue();
        Boolean actual2 = argumentCaptor2.getValue();

        assertThat(actual1).isEqualTo(12345L);
        assertThat(actual2).isEqualTo(true);

        catAdopterService.setPass(false, 12345L);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, times(2)).execute(argumentCaptor.capture());
        List<SendMessage> actual = argumentCaptor.getAllValues();

        Assertions.assertThat(actual.get(0).getParameters().get("chat_id")).isEqualTo(12345L);
        Assertions.assertThat(actual.get(0).getParameters().get("text"))
                .isEqualTo("Поздравляем, вы прошли испытательный срок");

        Assertions.assertThat(actual.get(1).getParameters().get("chat_id")).isEqualTo(12345L);
        Assertions.assertThat(actual.get(1).getParameters().get("text"))
                .isEqualTo("Увы, вы не прошли испытательный срок");
    }

    @Test
    void getRequestStatusTest() {
        CatAdopter entity = new CatAdopter();
        entity.setChatId(12345L);
        entity.setName("name");
        entity.setAge(18);
        entity.setEmail("mail@mail.ru");
        entity.setProneNumber("9812938");
        entity.setAddress("address");

        when(adoptersRepository.getRequestStatus(12345L)).thenReturn(false);

        catAdopterService.getRequestStatus(entity.getChatId());

        assertThat(catAdopterService.getRequestStatus(entity.getChatId())).isEqualTo(false);
    }

    @Test
    void setStatusTest() {
        catAdopterService.setStatus(12345L, true);

        ArgumentCaptor<Long> argumentCaptor1 = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> argumentCaptor2 = ArgumentCaptor.forClass(Boolean.class);
        verify(adoptersRepository).setStatus(argumentCaptor1.capture(), argumentCaptor2.capture());
        Long actual1 = argumentCaptor1.getValue();
        Boolean actual2 = argumentCaptor2.getValue();

        assertThat(actual1).isEqualTo(12345L);
        assertThat(actual2).isEqualTo(true);

        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        catAdopterService.setStatus(12345L, true, time);

        ArgumentCaptor<Timestamp> argumentCaptor5 = ArgumentCaptor.forClass(Timestamp.class);
        verify(adoptersRepository).setStatus(argumentCaptor1.capture(), argumentCaptor2.capture(), argumentCaptor5.capture());
        actual1 = argumentCaptor1.getValue();
        actual2 = argumentCaptor2.getValue();
        Timestamp actual3 = argumentCaptor5.getValue();

        assertThat(actual1).isEqualTo(12345L);
        assertThat(actual2).isEqualTo(true);
        assertThat(actual3).isEqualTo(time);
    }

    @Test
    void addDaysTest() {
        catAdopterService.addDays(12345L, 14);

        ArgumentCaptor<Long> argumentCaptor1 = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Integer> argumentCaptor2 = ArgumentCaptor.forClass(Integer.class);
        verify(adoptersRepository).addDays(argumentCaptor1.capture(), argumentCaptor2.capture());
        Long actual1 = argumentCaptor1.getValue();
        Integer actual2 = argumentCaptor2.getValue();

        assertThat(actual1).isEqualTo(12345L);
        assertThat(actual2).isEqualTo(14);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(12345L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Вам был продлен испытательный срок на " + 14 + " дней");
    }

    @Test
    void findAllExpiredTest() {
        List<Long> list = List.of(1L, 2L);
        when(adoptersRepository.findAllExpired()).thenReturn(list);
        assertThat(catAdopterService.findAllExpired()).isEqualTo(list);
    }

    @Test
    void findAllExpiredTooMuchTest() {
        List<Long> list = List.of(1L, 2L);
        when(adoptersRepository.findAllExpiredTooMuch()).thenReturn(list);
        assertThat(catAdopterService.findAllExpiredTooMuch()).isEqualTo(list);
    }
}