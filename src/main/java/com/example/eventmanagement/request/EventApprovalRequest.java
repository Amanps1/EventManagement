package com.example.eventmanagement.request;

import lombok.Data;

@Data
public class EventApprovalRequest {
    private Long eventId;
    private String reviewerUsername;
    private String comments;
    private String approvalConditions;
}
