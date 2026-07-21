package com.umesh.delivery_service.common.exception;

import com.umesh.delivery_service.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ErrorResponse handleNotFound(
                        ResourceNotFoundException ex,
                        HttpServletRequest request) {

                return ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .error(HttpStatus.NOT_FOUND.name())
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

        }

        @ExceptionHandler(Exception.class)
        public ErrorResponse handleGeneric(
                        Exception ex,
                        HttpServletRequest request) {

                return ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

        }

}