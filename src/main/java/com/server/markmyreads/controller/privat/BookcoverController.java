package com.server.markmyreads.controller.privat;

import com.server.markmyreads.controller.CommonController;
import com.server.markmyreads.domain.dto.BookcoverDto;
import com.server.markmyreads.usecase.bookcover.BookcoverFetch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("BookcoverController")
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class BookcoverController extends CommonController {

    private final BookcoverFetch fetch;

    @GetMapping("/{clippingsId}/bookcovers")
    public ResponseEntity<List<BookcoverDto>> findAll(@Valid @PathVariable("clippingsId") final Long clippingsId) {
        return fetch.findAll(clippingsId);
    }

}
