package com.server.markmyreads.handler.exception;

import com.server.markmyreads.domain.constant.ErrorReasonConstants;

public class EmptyFileException extends BadRequestException {

    public EmptyFileException() {
        super(ErrorReasonConstants.EMPTY_FILE);
    }

}
