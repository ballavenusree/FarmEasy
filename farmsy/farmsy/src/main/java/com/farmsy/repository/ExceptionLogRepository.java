package com.farmsy.repository;

import com.farmsy.model.ExceptionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ExceptionLogRepository extends JpaRepository<ExceptionLog, Long> {
    void deleteByTimestampBefore(LocalDateTime cutoffDate);
}