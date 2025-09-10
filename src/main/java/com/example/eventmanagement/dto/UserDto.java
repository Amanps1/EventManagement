package com.example.eventmanagement.dto;

import com.example.eventmanagement.model.User.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String emergencyContact;
    private boolean isActive;
    private boolean emailVerified;
    private LocalDateTime createdDate;
    private LocalDateTime lastLogin;
    private Role role;
    private Long zoneId;
    private List<Long> organizedEventIds;
    private List<Long> approvedEventIds;
    private List<Long> registeredEventIds;
    private List<Long> coordinatedZoneIds;
    private List<Long> reviewIds;
    private List<Long> receivedNotificationIds;
    private List<Long> sentNotificationIds;
    private List<Long> bookingIds;
    private List<Long> auditLogIds;
}
