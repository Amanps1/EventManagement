package com.example.eventmanagement.service.notification;

import com.example.eventmanagement.dto.NotificationDto;
import com.example.eventmanagement.request.NotificationRequestDto;

import java.util.List;

public interface INotificationService {
    List<NotificationDto> getUserNotifications(Long recipientId, int page, int size);

    NotificationDto markAsRead(Long id);

    void markAllAsRead(Long recipientId);

    void deleteNotification(Long id);

    NotificationDto sendNotification(NotificationRequestDto request);

    long getUnreadCount(Long recipientId);
}
