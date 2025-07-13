package com.server.markmyreads.domain.dto;

import com.server.markmyreads.domain.enumeration.ClippingsLocale;
import com.server.markmyreads.handler.exception.ClippingsExtractionErrorException;

import java.util.List;

public record ClippingsContext(List<String> blocks, ClippingsLocale locale) {

    public ClippingsContext(List<String> blocks, ClippingsLocale locale) {
        if (blocks == null || blocks.isEmpty()) {
            throw new ClippingsExtractionErrorException();
        }
        this.blocks = List.copyOf(blocks);
        this.locale = locale;
    }

}
