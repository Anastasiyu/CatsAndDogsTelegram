package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.Volunteer;
import com.example.catsanddogstelegram.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("volunteer")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(summary = "Занесение в базу данных волонтере",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Информация о волонтере, занесенная в базу",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            }, tags = "Volunteer"
    )
    @PostMapping()
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer){
        return ResponseEntity.status(HttpStatus.CREATED).body(volunteerService.createVolunteer(volunteer));
    }

    @Operation(summary = "Поиск данных о волонтере по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о волонтере",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            }, tags = "Volunteer"
    )
    @GetMapping("{id}")
    public ResponseEntity<Volunteer> readVolunteer(@PathVariable int id){
        return ResponseEntity.ok(volunteerService.readVolunteerById(id));
    }

    @Operation(summary = "Изменение данных о волонтере",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новые данные о волонтере",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            }, tags = "Volunteer"
    )
    @PutMapping("{id}")
    public ResponseEntity<Volunteer> updateVolunteer(@RequestBody Volunteer volunteer){
        return ResponseEntity.ok(volunteerService.updateVolunteer(volunteer));
    }

    @Operation(summary = "Удаление данных о волонтере",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленные данные о волонтере",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            }, tags = "Volunteer"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Volunteer> deleteVolunteer(@PathVariable int id){
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.ok().build();
    }
}
