package com.server.markmyreads.controller.privat;

import com.server.markmyreads.service.ClippingsValidatorService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController("FileController")
@RequestMapping(value = "/api/clippings", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClippingsController {

    private final ClippingsValidatorService validatorService;

    @PostMapping("/export")
    public ResponseEntity<String> convert(@NonNull @NotNull @RequestParam("file") final MultipartFile file) {

        validatorService.validate(file);

        return ResponseEntity.ok("Is myclippings");
    }

}
