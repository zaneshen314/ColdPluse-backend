package com.oocl.ita.web.core.exception;

import com.oocl.ita.web.common.constant.HttpStatus;
import com.oocl.ita.web.core.exception.response.CoreErrorResponse;
import com.oocl.ita.web.domain.vo.RespBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
        return RespBean.error(HttpStatus.FORBIDDEN, "NO PERMISSION");
    }

    @ExceptionHandler(EmailExistException.class)
    public RespBean<String> handleAccessDeniedException(EmailExistException e)
    {
        return RespBean.error(CoreErrorResponse.EMAIL_EXISTS_ERROR.getCode(), CoreErrorResponse.EMAIL_EXISTS_ERROR.getMessage());
    }

    @ExceptionHandler(EntityNotExistException.class)
    public RespBean<String> handleEntityNotExistException(EntityNotExistException e)
    {
        return RespBean.error(CoreErrorResponse.ENTITY_NOT_EXIST_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(ConcertInProgressException.class)
    public RespBean<String> handleConcertInProgressException(ConcertInProgressException ignored)
    {
        return RespBean.error(CoreErrorResponse.CONCERT_IN_PROGRESS_ERROR.getCode(), CoreErrorResponse.CONCERT_IN_PROGRESS_ERROR.getMessage());
    }
}
