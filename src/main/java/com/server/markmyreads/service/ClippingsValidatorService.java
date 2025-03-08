package com.server.markmyreads.service;

import org.springframework.web.multipart.MultipartFile;

public interface ClippingsValidatorService {

    void validate(final MultipartFile file);

}
