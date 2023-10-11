package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.Cat;
import com.example.catsanddogstelegram.exception.CatNotFoundException;
import com.example.catsanddogstelegram.repository.CatRepository;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatServiceTest {
    @Mock
    private CatRepository catRepository;
    @InjectMocks
    private CatService catService;

    @Test
    void createCatTest() {
        Cat cat = new Cat();
        cat.setAnimalId(1);
        cat.setAnimalName("name");
        cat.setAnimalAge(2);
        cat.setIsMale(false);
        cat.setRegisterDate(Timestamp.valueOf(LocalDateTime.now()));

        when(catRepository.save(cat)).thenReturn(cat);
        assertThat(catService.createCat(cat))
                .isNotNull()
                .isEqualTo(cat);
    }

    @Test
    void readCatTest() {
        Cat cat = new Cat();
        cat.setAnimalId(1);
        cat.setAnimalName("name");
        cat.setAnimalAge(2);
        cat.setIsMale(false);
        cat.setRegisterDate(Timestamp.valueOf(LocalDateTime.now()));

        when(catRepository.findById(cat.getAnimalId())).thenReturn(Optional.of(cat));
        assertThat(catService.readCat(1))
                .isNotNull()
                .isEqualTo(cat);

        when(catRepository.findById(cat.getAnimalId())).thenReturn(Optional.empty());
        assertThatExceptionOfType(CatNotFoundException.class)
                .isThrownBy(() -> catService.readCat(1));
    }

    @Test
    void updateCatTest() {
        Cat cat = new Cat();
        cat.setAnimalId(1);
        cat.setAnimalName("name");
        cat.setAnimalAge(2);
        cat.setIsMale(false);
        cat.setRegisterDate(Timestamp.valueOf(LocalDateTime.now()));

        Cat expected = new Cat();
        expected.setAnimalId(1);
        expected.setAnimalName("name");
        expected.setAnimalAge(3);
        expected.setIsMale(true);

        when(catRepository.findById(expected.getAnimalId())).thenReturn(Optional.of(cat));
        when(catRepository.save(cat)).thenReturn(cat);
        assertThat(catService.updateCat(expected))
                .isNotNull()
                .isEqualTo(cat);

        when(catRepository.findById(expected.getAnimalId())).thenReturn(Optional.empty());
        assertThatExceptionOfType(CatNotFoundException.class)
                .isThrownBy(() -> catService.updateCat(expected));
    }

    @Test
    void deleteCatTest() {
        Cat cat = new Cat();
        cat.setAnimalId(1);
        cat.setAnimalName("name");
        cat.setAnimalAge(2);
        cat.setIsMale(false);
        cat.setRegisterDate(Timestamp.valueOf(LocalDateTime.now()));

        when(catRepository.findById(cat.getAnimalId())).thenReturn(Optional.of(cat));
        catService.deleteCat(cat.getAnimalId());

        ArgumentCaptor<Cat> argumentCaptor = ArgumentCaptor.forClass(Cat.class);
        verify(catRepository).delete(argumentCaptor.capture());
        Cat actual = argumentCaptor.getValue();

        Assertions.assertThat(actual).isEqualTo(cat);
    }
}