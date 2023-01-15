package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.component.RecordMapper;
import com.example.catsanddogstelegram.entity.CatAdopter;
import com.example.catsanddogstelegram.exception.UserNotFoundException;
import com.example.catsanddogstelegram.record.CatAdopterRecord;
import com.example.catsanddogstelegram.repository.CatAdoptersRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Data
@Slf4j
public class CatAdopterService {
    private final CatAdoptersRepository adoptersRepository;
    private final CatService catService;
    private final RecordMapper mapper;

    public CatAdopterRecord createCatAdopter(CatAdopterRecord record){
        CatAdopter catAdopter = mapper.toEntity(record);
        catAdopter.setRegisterTime(Timestamp.valueOf(LocalDateTime.now()));
        return mapper.toRecord(adoptersRepository.save(catAdopter));
    }

    public CatAdopterRecord readCatAdopter(long chatId){
        return mapper.toRecord(adoptersRepository.findById(chatId).orElseThrow(UserNotFoundException::new));
    }

    public CatAdopterRecord updateCatAdopter(CatAdopterRecord adopter){
        CatAdopter catAdopter = mapper.toEntity(readCatAdopter(adopter.getChatId()));
        catAdopter.setName(adopter.getName());
        catAdopter.setAge(adopter.getAge());
        catAdopter.setEmail(adopter.getEmail());
        catAdopter.setProneNumber(adopter.getProneNumber());
        catAdopter.setAddress(adopter.getAddress());
        return mapper.toRecord(adoptersRepository.save(catAdopter));
    }

    public void deleteCatAdopter(long chatId){
        adoptersRepository.deleteById(chatId);
    }
}
