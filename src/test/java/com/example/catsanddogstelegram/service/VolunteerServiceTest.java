package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.Volunteer;
import com.example.catsanddogstelegram.repository.VolunteerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {

    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerService volunteerService;

    @Test
    void createVolunteerTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(true);
        when(volunteerRepository.save(testVolunteer)).thenReturn(testVolunteer);

        Assertions.assertThat(volunteerService.createVolunteer(testVolunteer)).isEqualTo(testVolunteer);
    }

    @Test
    void readVolunteerByIdTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(true);
        when(volunteerRepository.findById(testVolunteer.getId())).thenReturn(Optional.of(testVolunteer));
        Assertions.assertThat(volunteerService.readVolunteerById(testVolunteer.getId())).isEqualTo(testVolunteer);
    }

    @Test
    void updateVolunteerTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(true);

        when(volunteerRepository.findById(testVolunteer.getId())).thenReturn(Optional.of(testVolunteer));
        when(volunteerRepository.save(testVolunteer)).thenReturn(testVolunteer);

        Assertions.assertThat(volunteerService.updateVolunteer(testVolunteer)).isEqualTo(testVolunteer);
    }

    @Test
    void deleteVolunteerTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(true);

        when(volunteerRepository.findById(testVolunteer.getId())).thenReturn(Optional.of(testVolunteer));
        volunteerService.deleteVolunteer(testVolunteer.getId());
        ArgumentCaptor<Volunteer> argumentCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).delete(argumentCaptor.capture());
        Volunteer actual = argumentCaptor.getValue();

        Assertions.assertThat(actual).isEqualTo(testVolunteer);
    }


    @Test
    void onCommandReceivedTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(true);
        when(volunteerRepository.findByChatId(testVolunteer.getChatId())).thenReturn(Optional.of(testVolunteer));

        volunteerService.onCommandReceived(testVolunteer.getChatId());
        ArgumentCaptor<Volunteer> argumentCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).save(argumentCaptor.capture());
        Volunteer actual = argumentCaptor.getValue();

        Assertions.assertThat(actual).isEqualTo(testVolunteer);

    }

    @Test
    void offCommandReceivedTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(false);
        when(volunteerRepository.findByChatId(testVolunteer.getChatId())).thenReturn(Optional.of(testVolunteer));

        volunteerService.onCommandReceived(testVolunteer.getChatId());
        ArgumentCaptor<Volunteer> argumentCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).save(argumentCaptor.capture());
        Volunteer actual = argumentCaptor.getValue();

        Assertions.assertThat(actual).isEqualTo(testVolunteer);

    }
}