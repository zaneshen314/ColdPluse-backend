package com.oocl.ita.web.core.exception;

import com.oocl.ita.web.core.exception.response.CoreErrorResponse;
import com.oocl.ita.web.domain.vo.RespBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 
 * @author zane
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public RespBean<String> handleAccessDeniedException(AccessDeniedException e)
    {
        return RespBean.error(com.oocl.ita.web.common.constant.HttpStatus.FORBIDDEN, "NO PERMISSION");
    }

    @ExceptionHandler(EmailExistException.class)
    public RespBean<String> handleAccessDeniedException(EmailExistException e)
    {
        return RespBean.error(CoreErrorResponse.EMAIL_EXISTS_ERROR.getCode(), CoreErrorResponse.EMAIL_EXISTS_ERROR.getMessage());
    }

    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RespBean<String> handleEntityNotExistException(EntityNotExistException e)
    {
        return RespBean.error(CoreErrorResponse.ENTITY_NOT_EXIST_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(ConcertInProgressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBean<String> handleConcertInProgressException(ConcertInProgressException ignored)
    {
        return RespBean.error(CoreErrorResponse.CONCERT_IN_PROGRESS_ERROR.getCode(), CoreErrorResponse.CONCERT_IN_PROGRESS_ERROR.getMessage());
    }

    @ExceptionHandler(TicketLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBean<String> handleTicketLimitExceededException(TicketLimitExceededException ignored)
    {
        return RespBean.error(CoreErrorResponse.TICKET_LIMIT_EXCEEDED_ERROR.getCode(), CoreErrorResponse.TICKET_LIMIT_EXCEEDED_ERROR.getMessage());
    }

    @ExceptionHandler(NotEnoughTicketsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBean<String> handleNotEnoughTicketsException(NotEnoughTicketsException ignored)
    {
        return RespBean.error(CoreErrorResponse.NOT_ENOUGH_TICKETS_ERROR.getCode(), CoreErrorResponse.NOT_ENOUGH_TICKETS_ERROR.getMessage());
    }

    @ExceptionHandler(TicketSaleNotStartedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBean<String> handleTicketSaleNotStartedException(TicketSaleNotStartedException ignored)
    {
        return RespBean.error(CoreErrorResponse.TICKET_SALE_NOT_STARTED.getCode(), CoreErrorResponse.TICKET_SALE_NOT_STARTED.getMessage());
    }
}
