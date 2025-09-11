package com.example.eventmanagement.request;

import com.example.eventmanagement.model.Notification;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationRequestDto {
    private Long recipientId;
    private Long senderId;
    private String title;
    private String message;
    private Notification.NotificationType type;
    private LocalDateTime scheduledSendDate;
    private Notification.Priority priority;
}
