package com.server.markmyreads.service;

import com.server.markmyreads.domain.dto.BookcoverDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookcoverService {

    List<BookcoverDto> fetchBookcovers(@NotNull final MultipartFile file);

}
