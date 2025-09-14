package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientIdOrderByCreatedDateDesc(Long recipientId, Pageable pageable);

//    long countByRecipientIdAndReadFalse(Long recipientId);

//    long countUnreadByRecipient(Long recipientId);
}
