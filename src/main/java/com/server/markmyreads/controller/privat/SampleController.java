package com.server.markmyreads.controller.privat;

import com.server.markmyreads.domain.payload.SampleBody;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("SampleController")
@Validated
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SampleController {

    @PostMapping("/test")
    public ResponseEntity<String> testEndpoint(@Valid @RequestBody final SampleBody body) {

        if (body.getField().contains("mmr is great")) {
            return ResponseEntity
                    .ok("yes, it is");
        }
        else {
            throw new IllegalArgumentException("I dont think so");
        }

    }


}
