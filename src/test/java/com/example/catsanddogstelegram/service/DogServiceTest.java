package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.Dog;
import com.example.catsanddogstelegram.exception.DogNotFoundException;
import com.example.catsanddogstelegram.repository.DogRepository;
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
class DogServiceTest {

    @Mock
    private DogRepository dogRepository;
    @InjectMocks
    private DogService dogService;

    @Test
    void createDogTest() {
        Dog dog = new Dog();
        dog.setAnimalId(1);
        dog.setAnimalName("name");
        dog.setAnimalAge(2);
        dog.setIsMale(false);
        dog.setRegisterDate(Timestamp.valueOf(LocalDateTime.now()));
        dog.setAdopted(false);

        when(dogRepository.save(dog)).thenReturn(dog);
        assertThat(dogService.createDog(dog))
                .isNotNull()
                .isEqualTo(dog);
    }

    @Test
    void readDogTest() {
        Dog dog = new Dog();
        dog.setAnimalId(1);
        dog.setAnimalName("name");
        dog.setAnimalAge(2);
        dog.setIsMale(false);
        dog.setRegisterDate(Timestamp.valueOf(LocalDateTime.now()));
        dog.setAdopted(false);

        when(dogRepository.findById(dog.getAnimalId())).thenReturn(Optional.of(dog));
        assertThat(dogService.readDog(1))
                .isNotNull()
                .isEqualTo(dog);

        when(dogRepository.findById(dog.getAnimalId())).thenReturn(Optional.empty());
        assertThatExceptionOfType(DogNotFoundException.class)
                .isThrownBy(() -> dogService.readDog(1));
    }

    @Test
    void updateDogTest() {
        Dog dog = new Dog();
        dog.setAnimalId(1);
        dog.setAnimalName("name");
        dog.setAnimalAge(2);
        dog.setIsMale(false);
        dog.setRegisterDate(Timestamp.valueOf(LocalDateTime.now()));
        dog.setAdopted(false);

        Dog expected = new Dog();
        expected.setAnimalId(1);
        expected.setAnimalName("name");
        expected.setAnimalAge(3);
        expected.setIsMale(true);
        expected.setAdopted(false);

        when(dogRepository.findById(expected.getAnimalId())).thenReturn(Optional.of(dog));
        when(dogRepository.save(dog)).thenReturn(dog);
        assertThat(dogService.updateDog(expected))
                .isNotNull()
                .isEqualTo(dog);

        when(dogRepository.findById(expected.getAnimalId())).thenReturn(Optional.empty());
        assertThatExceptionOfType(DogNotFoundException.class)
                .isThrownBy(() -> dogService.updateDog(expected));
    }

    @Test
    void deleteDogTest() {
        Dog dog = new Dog();
        dog.setAnimalId(1);
        dog.setAnimalName("name");
        dog.setAnimalAge(2);
        dog.setIsMale(false);
        dog.setRegisterDate(Timestamp.valueOf(LocalDateTime.now()));
        dog.setAdopted(false);

        when(dogRepository.findById(dog.getAnimalId())).thenReturn(Optional.of(dog));
        dogService.deleteDog(dog.getAnimalId());

        ArgumentCaptor<Dog> argumentCaptor = ArgumentCaptor.forClass(Dog.class);
        verify(dogRepository).delete(argumentCaptor.capture());
        Dog actual = argumentCaptor.getValue();

        Assertions.assertThat(actual).isEqualTo(dog);
    }
}