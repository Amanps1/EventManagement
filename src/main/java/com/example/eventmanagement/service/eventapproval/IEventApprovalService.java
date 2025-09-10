package com.example.eventmanagement.service.eventapproval;

import com.example.eventmanagement.dto.EventApprovalDto;

import java.util.List;

public interface IEventApprovalService {
    EventApprovalDto submitForApproval(Long eventId);

    List<EventApprovalDto> getPendingApprovals();

    EventApprovalDto updateApprovalStatus(Long approvalId, String status, String comments);

    List<EventApprovalDto> getApprovalHistory();

    EventApprovalDto addApprovalComment(Long approvalId, String comment);
}
