package com.server.markmyreads.usecase.upload;

import com.server.markmyreads.domain.dto.SessionDto;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ClippingsUpload {

    ResponseEntity<SessionDto> upload(@NonNull @NotNull final MultipartFile myClippingsFile);

}
