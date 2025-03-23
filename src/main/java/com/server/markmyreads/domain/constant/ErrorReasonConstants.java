package com.server.markmyreads.domain.constant;

public final class ErrorReasonConstants {

    public static final String NO_FILE = "You have to provide at least one file";

    public static final String EMPTY_FILE = "The given file has no content";

    public static final String INVALID_FILE_EXTENSION = "The provided file must be in txt";

    public static final String INVALID_FILE_FORMAT = "File dont follow MyClippings structure";

    public static final String INVALID_EXPORT = "Invalid export method";

    public static final String EXTRACTION_ERROR = "Unable to extract information from file body";

    public static final String ZIP_PROCESSING_ERROR = "Unable to proccess zip content";
}
