package com.example.eventmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditLogDto {
    private Long id;
    private Long userId;
    private String username;
    private String action;
    private String entityType;
    private int entityId;
    private String oldValue;
    private String newValue;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime timeStamp;
}
