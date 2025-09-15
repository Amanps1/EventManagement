package com.example.eventmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "venue_name", nullable = false, length = 150)
    private String venueName;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "amenities", columnDefinition = "TEXT")
    private String amenities;

    @Column(name = "hourly_rate", precision = 8, scale = 2)
    private BigDecimal hourlyRate;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable = true;

    @Column(name = "contact_phone", length = 100)
    private String contactPhone;

    @Column(name = "accessibility_features", columnDefinition = "TEXT")
    private String accessibilityFeatures;

    // One venue hosts many events
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    // One venue has many resources
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resources> resources = new ArrayList<>();

    // One venue can have many bookings
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResourceBooking> bookings = new ArrayList<>();


}
