package com.server.markmyreads.service.impl;

import com.server.markmyreads.handler.exception.EmptyFileException;
import com.server.markmyreads.handler.exception.FileAbsentException;
import com.server.markmyreads.handler.exception.InvalidFileExtensionException;
import com.server.markmyreads.handler.exception.InvalidFileFormatException;
import com.server.markmyreads.service.ClippingsValidatorService;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service("ClippingsValidatorService")
public class ClippingsValidatorServiceImpl implements ClippingsValidatorService {

    @Override
    public void validate(final MultipartFile file) {

        if (file == null) {
            throw new FileAbsentException();
        }

        if (file.isEmpty()) {
            throw new EmptyFileException();
        }

        if (!isTxt(file.getOriginalFilename())) {
            throw new InvalidFileExtensionException();
        }

        if (!isMyClippings(file)) {
            throw new InvalidFileFormatException();
        }

    }

    private boolean isTxt(@NotBlank final String fileName) {
        return StringUtils.isNotBlank(fileName) && FilenameUtils.getExtension(fileName).equalsIgnoreCase("txt");
    }

    private boolean isMyClippings(final MultipartFile file) {

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isReadingBlock = false;

            while ((line = br.readLine()) != null) {
                if (line.trim().equals("==========")) {
                    if (isReadingBlock) {
                        return true;
                    }
                    isReadingBlock = true;
                } else if (!line.trim().isEmpty()) {
                    isReadingBlock = true;
                }
            }
        }
        catch (Exception e) {
            throw new InvalidFileFormatException();
        }

        return false;
    }

}