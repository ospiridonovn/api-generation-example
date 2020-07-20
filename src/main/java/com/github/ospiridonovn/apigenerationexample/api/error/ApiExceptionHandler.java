package com.github.ospiridonovn.apigenerationexample.api.error;

import com.github.ospiridonovn.apigenerationexample.model.ApiError;
import com.github.ospiridonovn.apigenerationexample.model.ApiValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiError> handleAllExceptions(Exception ex) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiError apiError = new ApiError();
        apiError.setStatus(status.name());
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ApiError> handleConstraintViolationExceptions(Exception ex) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError();
        apiError.setStatus(status.name());
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(status).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setStatus(status.name());
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(status).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiError response = new ApiError();
        response.setStatus(status.name());
        response.setMessage(ex.getMessage());
        response.setValidationErrors(new ArrayList<>());
        response.setTimestamp(OffsetDateTime.now());
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            ApiValidationError validationError = new ApiValidationError();
            validationError.setObjectName(error.getObjectName());
            validationError.setMessage(error.getDefaultMessage());
            if (error instanceof FieldError) {
                validationError.setField(((FieldError) error).getField());
                validationError.setRejectedValue(((FieldError) error).getRejectedValue());
            }
            response.getValidationErrors().add(validationError);
        }
        return ResponseEntity.status(status).body(response);
    }

}
