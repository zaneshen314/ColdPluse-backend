package com.oocl.ita.web.core.exception;

import com.oocl.ita.web.core.exception.response.CoreErrorResponse;

public class NotEnoughTicketsException extends RuntimeException {
    public NotEnoughTicketsException() {
        super(CoreErrorResponse.NOT_ENOUGH_TICKETS_ERROR.getMessage());
    }
}
