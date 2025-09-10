package com.example.eventmanagement.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="event")
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",nullable=false,length=200)
    private String title;

    @Column(name="description",nullable=false,columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="category",nullable=false)
    private Category category;

    @Column(name="start_datetime",nullable=false)
    private LocalDateTime startDatetime;

    @Column(name="end_datetime",nullable=false)
    private LocalDateTime endDatetime;

    @Column(name="location",nullable=false,length=200)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="venue_id",nullable=false)
    private Venue venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organizer_id",nullable=false)
    private User organizer;

    @Column(name="max_capacity",nullable=false)
    private int maxCapacity;

    @Column(name="current_registrations",nullable = false,columnDefinition="INT DEFAULT 0")
    private int currentRegistrations=0;

    @Column(name="registration_deadline")
    private LocalDateTime registrationDeadline;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable=false)
    private Status status;

    @Column(name="is_recurring",nullable=false,columnDefinition="BOOLEAN DEFAULT FALSE")
    private boolean isRecurring=false;

    @Column(name="recurrence_pattern",length=100)
    private String recurrencePattern;

    @Column(name="created_date",nullable=false,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",updatable = false)
    private LocalDateTime createdDate=LocalDateTime.now();


    @Column(name="updated_date",nullable=false,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate=LocalDateTime.now();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="approved_by")
    private User approvedBy;

    @OneToMany(mappedBy="event",cascade = CascadeType.ALL)
    private List<EventRegistration> eventRegistrations=new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventApprovals> approvals = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResourceBooking> resourceBookings = new ArrayList<>();

    public enum Category{
        SOCIAL,EDUCATIONAL,RECREATIONAL,CIVIC,EMERGENCY
    }

    public enum Status{
        DRAFT,PENDING_APPROVAL,APPROVED,CANCELLED,COMPLETED
    }
}
