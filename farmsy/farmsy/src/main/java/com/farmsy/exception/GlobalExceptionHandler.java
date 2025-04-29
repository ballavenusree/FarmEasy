package com.farmsy.exception;

import com.farmsy.model.ExceptionLog;
import com.farmsy.service.ExceptionLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ExceptionLogService exceptionLogService;

    @ExceptionHandler(FarmerNotFoundException.class)
    public ResponseEntity<Object> handleFarmerNotFoundException(
            FarmerNotFoundException ex,
            WebRequest request,
            HttpServletRequest httpRequest) {

        exceptionLogService.logException(ex, request, httpRequest);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CropUnavailableException.class)
    public ResponseEntity<Object> handleCropUnavailableException(
            CropUnavailableException ex,
            WebRequest request,
            HttpServletRequest httpRequest) {

        exceptionLogService.logException(ex, request, httpRequest);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(
            OrderNotFoundException ex,
            WebRequest request,
            HttpServletRequest httpRequest) {

        exceptionLogService.logException(ex, request, httpRequest);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(
            Exception ex,
            WebRequest request,
            HttpServletRequest httpRequest) {

        exceptionLogService.logException(ex, request, httpRequest);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "An error occurred while processing your request");
        body.put("details", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}