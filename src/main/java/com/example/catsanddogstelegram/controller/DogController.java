package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.Dog;
import com.example.catsanddogstelegram.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dog")
public class DogController {
    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @Operation(summary = "Занесение в базу данных о новой собаке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о собаке, занесенная в базу",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    )
            }, tags = "Dog"
    )
    @PostMapping
    public Dog create(@RequestBody Dog dog) {
        return dogService.create(dog);
    }

    @Operation(summary = "Поиск данных о собаке по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о собаке, найденная по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    )
            }, tags = "Dog"
    )
    @GetMapping("{id}")
    public ResponseEntity<Dog> read(@PathVariable(name = "id") long animalId) {
        Dog dog = dogService.read(animalId);
        return ResponseEntity.ok(dog);
    }

    @Operation(summary = "Изменение данных о собаке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новые данные о собаке",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    )
            }, tags = "Dog"
    )
    @PutMapping("{id}")
    public ResponseEntity<Dog> update(@PathVariable(name = "id") long animalId,
                                      @RequestBody Dog dog) {
        Dog foundDog = dogService.update(animalId, dog);
        return ResponseEntity.ok(foundDog);
    }

    @Operation(summary = "Удаление данных о собаке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленные данные о собаке",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    )
            }, tags = "Dog"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Dog> delete(@PathVariable(name = "id") long animalId) {
        dogService.delete(animalId);
        return ResponseEntity.ok().build();
    }
}
