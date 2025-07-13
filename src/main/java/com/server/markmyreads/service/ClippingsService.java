package com.server.markmyreads.service;


import com.server.markmyreads.domain.enumeration.ClippingsLocale;
import com.server.markmyreads.domain.jpa.Clippings;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface ClippingsService {

    Clippings save(@NonNull final MultipartFile file, ClippingsLocale locale);

}
