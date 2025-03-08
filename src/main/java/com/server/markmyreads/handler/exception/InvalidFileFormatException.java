package com.server.markmyreads.handler.exception;

import com.server.markmyreads.domain.constant.ErrorReasonConstants;

public class InvalidFileFormatException extends BadRequestException {

    public InvalidFileFormatException() {
        super(ErrorReasonConstants.INVALID_FILE_FORMAT);
    }

}