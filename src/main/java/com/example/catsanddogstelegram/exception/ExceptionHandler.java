package com.example.catsanddogstelegram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(CatNotFoundException.class)
    public ResponseEntity<String> handlerCatNotFoundException(CatNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Кошка с animalId = %d  не найдена!", e.getId()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DogNotFoundException.class)
    public ResponseEntity<String> handlerDogNotFoundException(DogNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Собака с animalId = %d  не найдена!", e.getId()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handlerUserNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Пользователь не найден!");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<String> handlerReportNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Отчет не найден!");
    }
}


