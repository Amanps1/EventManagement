package com.example.eventmanagement.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_registration")
public class EventRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="registration_date",nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",updatable=false)
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false)
    private Status status;

    @Column(name="special_requirements",columnDefinition = "TEXT")
    private String specialRequirements;

    @Column(name="check_in_time")
    private LocalDateTime checkInTime;

    @Column(name="feedback_rating")
    private Integer feedbackRating;
    @Column(name="feedback_comment",columnDefinition="TEXT")
    private String feedbackComment;
    public enum Status {
        REGISTERED, WAITLISTED, CANCELLED, ATTENDED, NO_SHOW
    }
}
