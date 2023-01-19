package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.component.RecordMapper;
import com.example.catsanddogstelegram.entity.CatAdopter;
import com.example.catsanddogstelegram.entity.Dog;
import com.example.catsanddogstelegram.entity.DogAdopter;
import com.example.catsanddogstelegram.record.DogAdopterRecord;
import com.example.catsanddogstelegram.repository.DogAdoptersRepository;
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
class DogAdopterServiceTest {

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private DogAdoptersRepository adoptersRepository;
    @Mock
    private RecordMapper mapper;
    @InjectMocks
    private DogAdopterService dogAdopterService;

    @Test
    void createDogAdopter() {
        DogAdopterRecord record = new DogAdopterRecord();
        record.setChatId(12345L);
        record.setName("name");
        record.setAge(18);
        record.setEmail("mail@mail.ru");
        record.setRegisterTime(Timestamp.valueOf(LocalDateTime.now()));
        record.setProneNumber("9812938");
        record.setAddress("address");
        record.setDogId(1);

        Dog dog = new Dog();
        dog.setAnimalId(1);

        DogAdopter entity = new DogAdopter();
        entity.setChatId(12345L);
        entity.setName("name");
        entity.setAge(18);
        entity.setEmail("mail@mail.ru");
        entity.setRegisterTime(Timestamp.valueOf(LocalDateTime.now()));
        entity.setProneNumber("9812938");
        entity.setAddress("address");
        entity.setDog(dog);

        when(mapper.toEntity(record)).thenReturn(entity);
        when(mapper.toRecord(entity)).thenReturn(record);
        when(adoptersRepository.save(entity)).thenReturn(entity);

        assertThat(dogAdopterService.createDogAdopter(record))
                .isNotNull()
                .isEqualToIgnoringGivenFields(record, "registerTime");
    }

    @Test
    void readDogAdopter() {
        DogAdopterRecord record = new DogAdopterRecord();
        record.setChatId(12345L);
        record.setName("name");
        record.setAge(18);
        record.setEmail("mail@mail.ru");
        record.setRegisterTime(Timestamp.valueOf(LocalDateTime.now()));
        record.setProneNumber("9812938");
        record.setAddress("address");
        record.setDogId(1);

        when(mapper.toRecord(any(DogAdopter.class))).thenReturn(record);
        when(adoptersRepository.findById(record.getChatId())).thenReturn(Optional.of(new DogAdopter()));

        assertThat(dogAdopterService.readDogAdopter(record.getChatId()))
                .isNotNull()
                .isEqualTo(record);
    }

    @Test
    void updateDogAdopter() {
        DogAdopterRecord record = new DogAdopterRecord();
        record.setChatId(12345L);
        record.setName("name");
        record.setAge(18);
        record.setEmail("mail@mail.ru");
        record.setProneNumber("9812938");
        record.setAddress("address");
        record.setDogId(1);

        Dog dog = new Dog();
        dog.setAnimalId(1);

        DogAdopter entity = new DogAdopter();
        entity.setChatId(12345L);
        entity.setName("name");
        entity.setAge(18);
        entity.setEmail("mail@mail.ru");
        entity.setProneNumber("9812938");
        entity.setAddress("address");
        entity.setDog(dog);

        when(mapper.toRecord(entity)).thenReturn(record);
        when(adoptersRepository.findById(record.getChatId())).thenReturn(Optional.of(entity));
        when(mapper.toEntity(record)).thenReturn(entity);
        when(adoptersRepository.save(entity)).thenReturn(entity);

        assertThat(dogAdopterService.updateDogAdopter(record))
                .isNotNull()
                .isEqualTo(record);
    }

    @Test
    void deleteDogAdopter() {
        Dog dog = new Dog();
        dog.setAnimalId(1);

        DogAdopter entity = new DogAdopter();
        entity.setChatId(12345L);
        entity.setName("name");
        entity.setAge(18);
        entity.setEmail("mail@mail.ru");
        entity.setProneNumber("9812938");
        entity.setAddress("address");
        entity.setDog(dog);

        when(mapper.toRecord(entity)).thenReturn(new DogAdopterRecord());
        when(mapper.toEntity(any(DogAdopterRecord.class))).thenReturn(entity);
        when(adoptersRepository.findById(entity.getChatId())).thenReturn(Optional.of(entity));
        dogAdopterService.deleteDogAdopter(entity.getChatId());

        ArgumentCaptor<DogAdopter> argumentCaptor = ArgumentCaptor.forClass(DogAdopter.class);
        verify(adoptersRepository).delete(argumentCaptor.capture());
        DogAdopter actual = argumentCaptor.getValue();

        Assertions.assertThat(actual).isEqualTo(entity);
    }

    @Test
    void setPassTest() {
        dogAdopterService.setPass(true, 12345L);

        ArgumentCaptor<Long> argumentCaptor1 = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> argumentCaptor2 = ArgumentCaptor.forClass(Boolean.class);
        verify(adoptersRepository).setPass(argumentCaptor2.capture(), argumentCaptor1.capture());
        Long actual1 = argumentCaptor1.getValue();
        Boolean actual2 = argumentCaptor2.getValue();

        assertThat(actual1).isEqualTo(12345L);
        assertThat(actual2).isEqualTo(true);

        dogAdopterService.setPass(false, 12345L);

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

        dogAdopterService.getRequestStatus(entity.getChatId());

        assertThat(dogAdopterService.getRequestStatus(entity.getChatId())).isEqualTo(false);
    }

    @Test
    void setStatusTest() {
        dogAdopterService.setStatus(12345L, true);

        ArgumentCaptor<Long> argumentCaptor1 = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> argumentCaptor2 = ArgumentCaptor.forClass(Boolean.class);
        verify(adoptersRepository).setStatus(argumentCaptor1.capture(), argumentCaptor2.capture());
        Long actual1 = argumentCaptor1.getValue();
        Boolean actual2 = argumentCaptor2.getValue();

        assertThat(actual1).isEqualTo(12345L);
        assertThat(actual2).isEqualTo(true);

        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        dogAdopterService.setStatus(12345L, true, time);

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
        dogAdopterService.addDays(12345L, 14);

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
        assertThat(dogAdopterService.findAllExpired()).isEqualTo(list);
    }

    @Test
    void findAllExpiredTooMuchTest() {
        List<Long> list = List.of(1L, 2L);
        when(adoptersRepository.findAllExpiredTooMuch()).thenReturn(list);
        assertThat(dogAdopterService.findAllExpiredTooMuch()).isEqualTo(list);
    }
}