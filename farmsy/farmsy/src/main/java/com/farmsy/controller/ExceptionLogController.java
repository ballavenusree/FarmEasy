package com.farmsy.controller;

import com.farmsy.model.ExceptionLog;
import com.farmsy.repository.ExceptionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exception-logs")
public class ExceptionLogController {

    @Autowired
    private ExceptionLogRepository exceptionLogRepository;

    @GetMapping
    public List<ExceptionLog> getAllExceptionLogs() {
        return exceptionLogRepository.findAll();
    }
}