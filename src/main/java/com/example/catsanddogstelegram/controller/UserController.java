package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.User;
import com.example.catsanddogstelegram.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Поиск пользователей по имени",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные пользователи",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )
                    )
            }, tags = "User"
    )
    @GetMapping
    public ResponseEntity<List<User>> findUserByName(@Parameter(description = "Имя", example = "Иван")
                                                     @RequestParam String userName) {
        if (userName.isBlank()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.findUserByName(userName));
    }

}
