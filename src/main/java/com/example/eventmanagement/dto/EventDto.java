package com.example.eventmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String status;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private String location;
    private String venueName;
    private String organizerName;
    private int maxCapacity;
    private int currentRegistrations;
    private LocalDateTime registrationDeadline;
    private boolean isRecurring;
    private String recurrencePattern;
}
