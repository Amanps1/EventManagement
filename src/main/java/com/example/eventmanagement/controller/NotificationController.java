package com.example.eventmanagement.controller;

import com.example.eventmanagement.dto.NotificationDto;
import com.example.eventmanagement.request.NotificationRequestDto;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.service.notification.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final INotificationService notificationService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUserNotifications(
            @RequestParam Long recipientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<NotificationDto> list = notificationService.getUserNotifications(recipientId, page, size);
        return ResponseEntity.ok(new ApiResponse("Success", list));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ApiResponse> markAsRead(@PathVariable Long id) {
        NotificationDto dto = notificationService.markAsRead(id);
        return ResponseEntity.ok(new ApiResponse("Marked as read", dto));
    }

    @PutMapping("/mark-all-read")
    public ResponseEntity<ApiResponse> markAllAsRead(@RequestParam Long recipientId) {
        notificationService.markAllAsRead(recipientId);
        return ResponseEntity.ok(new ApiResponse("All notifications marked as read", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(new ApiResponse("Deleted successfully", null));
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendNotification(@RequestBody NotificationRequestDto request) {
        NotificationDto dto = notificationService.sendNotification(request);
        return ResponseEntity.ok(new ApiResponse("Notification sent", dto));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse> getUnreadCount(@RequestParam Long recipientId) {
        long count = notificationService.getUnreadCount(recipientId);
        return ResponseEntity.ok(new ApiResponse("Success", count));
    }
}
