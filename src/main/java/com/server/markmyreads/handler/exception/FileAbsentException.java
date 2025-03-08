package com.server.markmyreads.handler.exception;

import com.server.markmyreads.domain.constant.ErrorReasonConstants;

public class FileAbsentException extends BadRequestException {

    public FileAbsentException() {
        super(ErrorReasonConstants.NO_FILE);
    }
}
