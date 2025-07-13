package com.server.markmyreads.domain.dto;

import java.time.LocalDate;

public record ParsedClippingDto(
    String title,
    String author,
    String content,
    LocalDate date
) {}
