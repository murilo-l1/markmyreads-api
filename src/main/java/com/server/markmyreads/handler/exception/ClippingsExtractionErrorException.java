package com.server.markmyreads.handler.exception;

import com.server.markmyreads.domain.constant.ErrorReasonConstants;
import org.springframework.http.HttpStatus;

public class ClippingsExtractionErrorException extends NotContentException {

    public ClippingsExtractionErrorException() {
        super(ErrorReasonConstants.EXTRACTION_ERROR);
    }

}
