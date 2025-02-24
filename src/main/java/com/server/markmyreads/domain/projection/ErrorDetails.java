package com.server.markmyreads.domain.projection;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDetails {

    private Integer status;

    private String error;

    private String message;

    private String path;

}
