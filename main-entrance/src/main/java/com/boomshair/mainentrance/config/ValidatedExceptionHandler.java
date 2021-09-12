package com.boomshair.mainentrance.config;

import com.boomshair.mainentrance.entity.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class ValidatedExceptionHandler {

    Logger logger = LogManager.getLogger(ValidatedExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Mono<Response> showException(ConstraintViolationException exception) {
        Response response = new Response();
        response.setErrorStatus(Response.ErrorStatus.ERROR);
        response.setErrorNo(Response.ErrorStatus.ERROR.getErrorNo());
        // 只提示第一个错
        Set<ConstraintViolation<?>> constraintViolationExceptionSet = exception.getConstraintViolations();
        StringBuilder stringBuilder = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : constraintViolationExceptionSet) {
            stringBuilder.append(constraintViolation.getMessage()).append("  ");
        }
        response.setMessage(stringBuilder.toString());
        return Mono.just(response);

    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Mono<Response> commException(Exception exception) {
        Response response = new Response();
        response.setErrorStatus(Response.ErrorStatus.ERROR);
        response.setErrorNo(Response.ErrorStatus.ERROR.getErrorNo());
        logger.error(exception.getMessage());
        if (exception instanceof DataIntegrityViolationException) {
            response.setMessage("数据插入时违反了唯一性约束");
        } else {
            response.setMessage(exception.getMessage());
        }
        return Mono.just(response);
    }

}
