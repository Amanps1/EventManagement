package com.example.eventmanagement.dto;

import lombok.Data;

@Data
public class SystemStatisticsDto {
    private long totalUsers;
    private long activeEvents;
    private long totalAuditLogs;
}
