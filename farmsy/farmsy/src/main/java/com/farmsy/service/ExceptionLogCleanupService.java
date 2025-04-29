package com.farmsy.service;

import com.farmsy.repository.ExceptionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExceptionLogCleanupService {

    @Autowired
    private ExceptionLogRepository exceptionLogRepository;

    // Runs every day at 2 AM
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupOldLogs() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30); // Keep logs for 30 days
        exceptionLogRepository.deleteByTimestampBefore(cutoffDate);
    }
}