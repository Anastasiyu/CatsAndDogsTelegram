package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.entity.CatReport;
import com.example.catsanddogstelegram.service.CatReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("cat/report")
public class CatReportController {
    private final CatReportService reportService;


    public CatReportController(CatReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Поиск отчетов по дате",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "список отчетов по дате",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = CatReport.class))
                            )
                    )
            }, tags = "Report"
    )
    @GetMapping()
    public ResponseEntity<List<CatReport>> findAllByDate(@RequestParam String date){
        return ResponseEntity.ok(reportService.readAllByDay(date));
    }

    @Operation(summary = "Удаление отчетов по id усыновителя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленные отчеты",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = CatReport.class))
                            )
                    )
            }, tags = "Report"
    )
    @DeleteMapping()
    public ResponseEntity<List<CatReport>> deleteByChatId(@RequestParam long chatId){
        reportService.clear(chatId);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Отображение изображение из отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "картинка",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = byte[].class)
                            )
                    )
            }, tags = "Report"
    )
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) throws IOException {
        byte[] image = reportService.readFromFile(id);
        return ResponseEntity.ok()
                .contentLength(image.length)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
}
