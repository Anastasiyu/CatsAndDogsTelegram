package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.component.RecordMapper;
import com.example.catsanddogstelegram.entity.Dog;
import com.example.catsanddogstelegram.entity.DogAdopter;
import com.example.catsanddogstelegram.record.DogAdopterRecord;
import com.example.catsanddogstelegram.repository.DogAdoptersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class DogAdopterServiceTest {
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
}