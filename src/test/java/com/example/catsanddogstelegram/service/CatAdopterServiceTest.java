package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.component.RecordMapper;
import com.example.catsanddogstelegram.entity.Cat;
import com.example.catsanddogstelegram.entity.CatAdopter;
import com.example.catsanddogstelegram.record.CatAdopterRecord;
import com.example.catsanddogstelegram.repository.CatAdoptersRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatAdopterServiceTest {

    @Mock
    private CatAdoptersRepository adoptersRepository;
    @Mock
    private RecordMapper mapper;
    @InjectMocks
    private CatAdopterService catAdopterService;

    @Test
    void createCatAdopter() {
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
    void readCatAdopter() {
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
    void updateCatAdopter() {
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
    void deleteCatAdopter() {
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
}