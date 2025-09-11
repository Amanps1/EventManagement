package com.example.eventmanagement.service.admin;

import com.example.eventmanagement.dto.AuditLogDto;
import com.example.eventmanagement.dto.SystemHealthDto;
import com.example.eventmanagement.dto.SystemStatisticsDto;

import java.util.List;

public interface IAdminService {
    SystemHealthDto getSystemHealth();

    List<AuditLogDto> getAuditLogs();

    List<AuditLogDto> getUserActivityLogs(Long userId);

    String triggerBackup();

    SystemStatisticsDto getSystemStatistics();

    String updateSystemSettings(String key, String value);

    String getSystemSettings();
}
