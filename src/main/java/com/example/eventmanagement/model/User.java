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
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="username", unique=true, nullable=false, length=50)
    private String username;

    @Column(name="email", unique=true, nullable=false, length=100)
    private String email;

    @Column(name="password_hash", nullable=false, length=255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable=false)
    private Role role;

    @Column(name="first_name", nullable=false, length=50)
    private String firstName;

    @Column(name="last_name", nullable=false, length=50)
    private String lastName;

    @Column(name="address", nullable=false, length=200)
    private String address;

    @Column(name="phone_number", nullable=false, length=15)
    private String phoneNumber;

    @Column(name="emergency_contact", length=100)
    private String emergencyContact;

    @Column(name="created_date", nullable=false, updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name="last_login")
    private LocalDateTime lastLogin = LocalDateTime.now();

    @Column(name="is_active", nullable=false, columnDefinition="BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;

    @Column(name="email_verified", nullable=false, columnDefinition="BOOLEAN DEFAULT FALSE")
    private boolean emailVerified = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuditLog> auditLogs = new ArrayList<>();

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
    private List<Event> organizedEvents = new ArrayList<>();

    @OneToMany(mappedBy = "approvedBy", cascade = CascadeType.ALL)
    private List<Event> approvedEvents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<EventRegistration> eventRegistrations = new ArrayList<>();

    @OneToMany(mappedBy = "coordinator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zone> coordinatedZones = new ArrayList<>();


    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventApprovals> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> receivedNotifications = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> sentNotifications = new ArrayList<>();

    @OneToMany(mappedBy = "bookedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResourceBooking> bookings = new ArrayList<>();

    public enum Role {
        RESIDENT, EVENT_ORGANIZER, ZONE_COORDINATOR, COMMUNITY_MANAGER, ADMIN
    }
}
