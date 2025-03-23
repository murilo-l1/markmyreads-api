package com.server.markmyreads.handler.exception;

import com.server.markmyreads.domain.constant.ErrorReasonConstants;

public class ZipProcessingErrorException extends BadRequestException{

    public ZipProcessingErrorException() {
        super(ErrorReasonConstants.ZIP_PROCESSING_ERROR);
    }

}
