package com.oocl.ita.web.core.exception;

import com.oocl.ita.web.core.exception.response.CoreErrorResponse;

public class TicketSaleNotStartedException extends RuntimeException {
    public TicketSaleNotStartedException() {
        super(CoreErrorResponse.TICKET_SALE_NOT_STARTED.getMessage());
    }
}
