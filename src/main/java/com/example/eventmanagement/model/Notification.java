package com.example.eventmanagement.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name="title",length = 200, nullable = false)
    private String title;

    @Column(name="message",columnDefinition = "TEXT", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name="type",nullable = false)
    private NotificationType type;

    @Column(name = "is_read", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isRead = false;

    @Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate=LocalDateTime.now();

    @Column(name = "scheduled_send_date")
    private LocalDateTime scheduledSendDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;


    public enum NotificationType {
        EVENT_UPDATE,
        REGISTRATION_CONFIRMATION,
        REMINDER,
        COMMUNITY_ALERT,
        SYSTEM_NOTIFICATION
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }

}
