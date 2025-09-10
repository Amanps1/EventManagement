package com.example.eventmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventApprovalDto {
    private Long id;
    private Long eventId;
    private String reviewerUsername;
    private String status;
    private LocalDateTime reviewDate;
    private String comments;
    private String approvalConditions;
}
