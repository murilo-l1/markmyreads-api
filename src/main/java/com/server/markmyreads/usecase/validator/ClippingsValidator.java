package com.server.markmyreads.usecase.validator;

import org.springframework.web.multipart.MultipartFile;

public interface ClippingsValidator {

    void validate(final MultipartFile file);

}
