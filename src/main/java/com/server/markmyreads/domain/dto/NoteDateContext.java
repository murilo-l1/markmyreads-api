package com.server.markmyreads.domain.dto;

import java.time.LocalDate;
import java.util.Locale;

public record NoteDateContext(LocalDate date, Locale locale) { }
