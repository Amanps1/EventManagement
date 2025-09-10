package com.example.eventmanagement.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="event_approvals")
public class EventApprovals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false)
    private Status status;

    @Column(name = "review_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime reviewDate;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Column(name = "approval_conditions", columnDefinition = "TEXT")
    private String approvalConditions;

    public enum Status {
        PENDING, APPROVED, REJECTED, NEEDS_REVISION
    }
}
