package com.example.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

import static com.example.util.DateTimeUtil.formatattedDateTime;

@SuppressWarnings("all")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public Map<String, Object> handleProductNotFoundException(ProductNotFoundException ex, HttpServletRequest request, WebRequest webRequest) {

        ServletWebRequest servletRequest =  (ServletWebRequest) request;

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", formatattedDateTime());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("exception", ex.getClass().getSimpleName());
        errorResponse.put("error", ex.getMessage());
        errorResponse.put("uri:", request.getRequestURI());
        errorResponse.put("method:", request.getMethod());
        errorResponse.put("uri", servletRequest.getRequest().getRequestURI());
        return errorResponse;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        if (ex instanceof HttpMediaTypeNotAcceptableException) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Unsupported media type");
        }
        if (ex instanceof NoResourceFoundException) {  //NoResourceFoundException - since 6.1
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid request url");
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
