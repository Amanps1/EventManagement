package com.example.eventmanagement.service.analytics;

import com.example.eventmanagement.dto.AnalyticDto;
import com.example.eventmanagement.dto.AuditLogDto;

import java.util.List;

public interface IAnalyticsService {
    AnalyticDto getSystemHealth();

    List<AuditLogDto> getAuditLogs();
}
