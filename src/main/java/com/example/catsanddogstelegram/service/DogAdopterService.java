package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.component.RecordMapper;
import com.example.catsanddogstelegram.entity.DogAdopter;
import com.example.catsanddogstelegram.exception.UserNotFoundException;
import com.example.catsanddogstelegram.record.DogAdopterRecord;
import com.example.catsanddogstelegram.repository.DogAdoptersRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Data
@Slf4j
public class DogAdopterService {
    private final DogAdoptersRepository adoptersRepository;
    private final RecordMapper mapper;

    public DogAdopterRecord createDogAdopter(DogAdopterRecord record){
        DogAdopter dogAdopter = mapper.toEntity(record);
        dogAdopter.setRegisterTime(Timestamp.valueOf(LocalDateTime.now()));
        return mapper.toRecord(adoptersRepository.save(dogAdopter));
    }

    public DogAdopterRecord readDogAdopter(long chatId){
        return mapper.toRecord(adoptersRepository.findById(chatId).orElseThrow(UserNotFoundException::new));
    }

    public DogAdopterRecord updateDogAdopter(DogAdopterRecord adopter){
        DogAdopter dogAdopter = mapper.toEntity(readDogAdopter(adopter.getChatId()));
        dogAdopter.setName(adopter.getName());
        dogAdopter.setAge(adopter.getAge());
        dogAdopter.setEmail(adopter.getEmail());
        dogAdopter.setProneNumber(adopter.getProneNumber());
        dogAdopter.setAddress(adopter.getAddress());
        return mapper.toRecord(adoptersRepository.save(dogAdopter));
    }

    public void deleteDogAdopter(long chatId){
        adoptersRepository.deleteById(chatId);
    }
}
