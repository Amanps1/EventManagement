package com.example.eventmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id",nullable=false)
    private User user;

    @Column(name="action",nullable=false,length=100)
    private String action;

    @Column(name="entity_type",nullable=false,length=50)
    private String entityType;

    @Column(name="entity_id",nullable=false)
    private int entityId;

    @Column(name="old_value",columnDefinition="JSON")
    private String oldValue;

    @Column(name="new_value",columnDefinition="JSON")
    private String newValue;

    @Column(name="ip_address",length=45)
    private String ipAddress;

    @Column(name="user_agent",length=500)
    private String userAgent;

    @Column(name="time_stamp",nullable=false,updatable=false)
    private LocalDateTime timeStamp=LocalDateTime.now();
}
