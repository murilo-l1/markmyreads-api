package com.server.markmyreads.handler.exception;

import com.server.markmyreads.domain.constant.ErrorReasonConstants;

public class InvalidExportMethodException extends BadRequestException {

    public InvalidExportMethodException() {
        super(ErrorReasonConstants.INVALID_EXPORT);
    }
}
