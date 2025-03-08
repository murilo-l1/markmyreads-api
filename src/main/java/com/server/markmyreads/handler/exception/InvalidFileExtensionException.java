package com.server.markmyreads.handler.exception;

import com.server.markmyreads.domain.constant.ErrorReasonConstants;

public class InvalidFileExtensionException extends BadRequestException {

    public InvalidFileExtensionException() {
        super(ErrorReasonConstants.INVALID_FILE_EXTENSION);
    }
}
