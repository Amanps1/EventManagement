package com.example.eventmanagement.service.notification;

import com.example.eventmanagement.dto.NotificationDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.model.Notification;
import com.example.eventmanagement.model.User;
import com.example.eventmanagement.repository.NotificationRepository;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.request.NotificationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepo;
    private final UserRepository userRepo;

    @Override
    public List<NotificationDto> getUserNotifications(Long recipientId, int page, int size) {
        return notificationRepo.findByRecipientIdOrderByCreatedDateDesc(recipientId, PageRequest.of(page, size))
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto markAsRead(Long id) {
        Notification notification = notificationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        notification.setRead(true);
        return toDto(notificationRepo.save(notification));
    }

    @Override
    public void markAllAsRead(Long recipientId) {
        List<Notification> notifications =
                notificationRepo.findByRecipientIdOrderByCreatedDateDesc(recipientId, Pageable.unpaged());
        notifications.forEach(n -> n.setRead(true));
        notificationRepo.saveAll(notifications);
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepo.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found");
        }
        notificationRepo.deleteById(id);
    }

    @Override
    public NotificationDto sendNotification(NotificationRequestDto request) {
        User recipient = userRepo.findById(request.getRecipientId())
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));
        User sender = userRepo.findById(request.getSenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

        Notification n = new Notification();
        n.setRecipient(recipient);
        n.setSender(sender);
        n.setTitle(request.getTitle());
        n.setMessage(request.getMessage());
        n.setType(request.getType());
        n.setScheduledSendDate(request.getScheduledSendDate());
        n.setPriority(request.getPriority());

        return toDto(notificationRepo.save(n));
    }

    @Override
    public long getUnreadCount(Long recipientId) {
        return notificationRepo.countUnreadByRecipient(recipientId);
    }

    private NotificationDto toDto(Notification n) {
        NotificationDto dto = new NotificationDto();

        dto.setId(n == null ? null : n.getId());
        dto.setTitle(n == null ? null : n.getTitle());
        dto.setMessage(n == null ? null : n.getMessage());
        dto.setType(n != null && n.getType() != null ? n.getType().name() : null);
        dto.setRead(n != null && n.isRead()); // entity field remains `isRead`, use isRead() here
        dto.setCreatedDate(n == null ? null : n.getCreatedDate());
        dto.setScheduledSendDate(n == null ? null : n.getScheduledSendDate());
        dto.setPriority(n != null && n.getPriority() != null ? n.getPriority().name() : null);

        dto.setSenderId(
                (n != null && n.getSender() != null) ? n.getSender().getId() : null
        );
        dto.setRecipientId(
                (n != null && n.getRecipient() != null) ? n.getRecipient().getId() : null
        );
        return dto;
    }
}
