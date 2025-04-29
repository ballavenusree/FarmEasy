package com.farmsy.service;

import com.farmsy.model.ExceptionLog;
import com.farmsy.repository.ExceptionLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@Service
public class ExceptionLogService {

    @Autowired
    private ExceptionLogRepository exceptionLogRepository;

    public void logException(Exception ex, WebRequest request, HttpServletRequest httpRequest) {
        ExceptionLog log = new ExceptionLog();
        log.setExceptionType(ex.getClass().getName());
        log.setMessage(ex.getMessage());
        log.setStackTrace(getStackTraceAsString(ex));
        log.setEndpoint(httpRequest.getRequestURI());
        log.setHttpMethod(httpRequest.getMethod());
        log.setTimestamp(LocalDateTime.now());

        // Log request parameters if available
        if (request != null) {
            String params = request.getParameterMap().toString();
            log.setRequestParams(params.length() > 1000 ? params.substring(0, 1000) : params);
        }

        exceptionLogRepository.save(log);
    }

    private String getStackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}