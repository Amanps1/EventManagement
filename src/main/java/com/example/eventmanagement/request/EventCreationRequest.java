package com.example.eventmanagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCreationRequest {
    private String title;
    private String description;
    private String category;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private String location;
    private String venueName;
    private String organizerName;
    private int maxCapacity;
    private LocalDateTime registrationDeadline;
    private boolean isRecurring;
    private String recurrencePattern;
}
