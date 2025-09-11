package com.example.eventmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;
    private String title;
    private String message;
    private String type;
    private boolean isRead;
    private LocalDateTime createdDate;
    private LocalDateTime scheduledSendDate;
    private String priority;
    private Long senderId;
    private Long recipientId;
}
