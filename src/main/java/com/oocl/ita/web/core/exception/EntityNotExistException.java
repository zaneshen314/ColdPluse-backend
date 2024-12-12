package com.oocl.ita.web.core.exception;

import com.oocl.ita.web.core.exception.response.CoreErrorResponse;

public class EntityNotExistException extends RuntimeException{
    public EntityNotExistException(String entity) {
        super(String.format(CoreErrorResponse.ENTITY_NOT_EXIST_ERROR.getMessage(), entity));
    }
}
