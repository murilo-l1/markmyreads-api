package com.server.markmyreads.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public interface HtmlToPdfService {

    byte[] toPdfBytes(@NonNull @NotBlank final String htmlContent);

}
