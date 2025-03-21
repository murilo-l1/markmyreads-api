package com.server.markmyreads.usecase.validator.impl;

import com.server.markmyreads.domain.constant.ClippingsConstants;
import com.server.markmyreads.handler.exception.EmptyFileException;
import com.server.markmyreads.handler.exception.FileAbsentException;
import com.server.markmyreads.handler.exception.InvalidFileExtensionException;
import com.server.markmyreads.handler.exception.InvalidFileFormatException;
import com.server.markmyreads.usecase.validator.ClippingsValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service("ClippingsValidator")
public class ClippingsValidatorImpl implements ClippingsValidator {

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
        return FilenameUtils.getExtension(fileName).equalsIgnoreCase("txt");
    }

    private boolean isMyClippings(@NonNull final MultipartFile file) {

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isReadingBlock = false;

            while ((line = br.readLine()) != null) {
                if (line.trim().equals(ClippingsConstants.BLOCK_DELIMITER)) {
                    if (isReadingBlock) {
                        return true;
                    }
                    isReadingBlock = true;
                } else if (StringUtils.isNotBlank(line)) {
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