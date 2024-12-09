package com.oocl.ita.web.core.exception;

import com.oocl.ita.web.core.exception.response.CoreErrorResponse;

public class ConcertInProgressException extends RuntimeException {
    public ConcertInProgressException() {
        super(CoreErrorResponse.CONCERT_IN_PROGRESS_ERROR.getMessage());
    }
}
