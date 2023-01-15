package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.Cat;
import com.example.catsanddogstelegram.service.CatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cat")
public class CatController {
    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @Operation(summary = "Занесение в базу данных о новой кошке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о кошке, занесенная в базу",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            }, tags = "Cat"
    )
    @PostMapping
    public Cat create(@RequestBody Cat cat) {
        return catService.create(cat);
    }

    @Operation(summary = "Поиск данных о кошке по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о кошке, найденная по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            }, tags = "Cat"
    )
    @GetMapping("{id}")
    public ResponseEntity<Cat> read(@PathVariable(name = "id") int animalId) {
        Cat cat = catService.read(animalId);
        return ResponseEntity.ok(cat);
    }

    @Operation(summary = "Изменение данных о кошке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новые данные о кошке",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            }, tags = "Cat"
    )
    @PutMapping("{id}")
    public ResponseEntity<Cat> update(@PathVariable(name = "id") int animalId,
                                      @RequestBody Cat cat) {
        Cat foundCat = catService.update(animalId, cat);
        return ResponseEntity.ok(foundCat);
    }

    @Operation(summary = "Удаление данных о кошке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленные данные о кошке",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            }, tags = "Cat"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Cat> delete(@PathVariable(name = "id") int animalId) {
        catService.delete(animalId);
        return ResponseEntity.ok().build();
    }
}
