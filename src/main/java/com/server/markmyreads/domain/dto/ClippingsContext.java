package com.server.markmyreads.domain.dto;

import com.server.markmyreads.handler.exception.ClippingsExtractionErrorException;

import java.util.List;

public record ClippingsContext(List<String> blocks) {

    public ClippingsContext(List<String> blocks) {
        if (blocks == null || blocks.isEmpty()) {
            throw new ClippingsExtractionErrorException();
        }
        this.blocks = List.copyOf(blocks);
    }

}
