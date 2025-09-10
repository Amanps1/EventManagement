package com.example.eventmanagement.dto;

import lombok.*;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRegistrationDto {
    private Long id;
    private Long eventId;
    private String username;
    private LocalDateTime registrationDate;
    private String status;
    private String specialRequirements;
    private LocalDateTime checkInTime;
    private Integer feedbackRating;
    private String feedbackComment;
}
