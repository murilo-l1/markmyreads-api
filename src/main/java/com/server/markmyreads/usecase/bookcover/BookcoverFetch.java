package com.server.markmyreads.usecase.bookcover;

import com.server.markmyreads.domain.dto.BookcoverDto;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookcoverFetch {

    ResponseEntity<List<BookcoverDto>> findAll(@NonNull final MultipartFile file);

}
