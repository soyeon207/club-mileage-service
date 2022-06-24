package com.triple.travelerclubservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler({
            IllegalStateException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage handleBadRequest(Exception ex) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.name(), ex.getMessage());
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorMessage {
        private String code;
        private String message;
    }

}
