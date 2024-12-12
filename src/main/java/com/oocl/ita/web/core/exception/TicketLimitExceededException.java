package com.oocl.ita.web.core.exception;

import com.oocl.ita.web.core.exception.response.CoreErrorResponse;

public class TicketLimitExceededException extends RuntimeException {
    public TicketLimitExceededException() {
        super(CoreErrorResponse.TICKET_LIMIT_EXCEEDED_ERROR.getMessage());
    }

    public TicketLimitExceededException(String message) {
        super(message);
    }
}
