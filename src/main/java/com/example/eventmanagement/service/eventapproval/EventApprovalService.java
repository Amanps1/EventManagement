package com.example.eventmanagement.service.eventapproval;

import com.example.eventmanagement.dto.EventApprovalDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.EventApprovals;
import com.example.eventmanagement.repository.EventApprovalsRepository;
import com.example.eventmanagement.repository.EventManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventApprovalService implements  IEventApprovalService {
    private final EventApprovalsRepository approvalRepo;
    private final EventManagementRepository eventRepo;

    @Override
    public EventApprovalDto submitForApproval(Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        EventApprovals approval = new EventApprovals();
        approval.setEvent(event);
        approval.setStatus(EventApprovals.Status.PENDING);
        approval.setReviewDate(LocalDateTime.now());

        return toDto(approvalRepo.save(approval));
    }

    @Override
    public List<EventApprovalDto> getPendingApprovals() {
        return approvalRepo.findByStatus(EventApprovals.Status.PENDING)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EventApprovalDto updateApprovalStatus(Long approvalId, String status, String comments) {
        EventApprovals approval = approvalRepo.findById(approvalId)
                .orElseThrow(() -> new ResourceNotFoundException("Approval not found"));

        approval.setStatus(EventApprovals.Status.valueOf(status.toUpperCase()));
        approval.setComments(comments);
        approval.setReviewDate(LocalDateTime.now());

        return toDto(approvalRepo.save(approval));
    }

    @Override
    public List<EventApprovalDto> getApprovalHistory() {
        return approvalRepo.findAll()
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EventApprovalDto addApprovalComment(Long approvalId, String comment) {
        EventApprovals approval = approvalRepo.findById(approvalId)
                .orElseThrow(() -> new ResourceNotFoundException("Approval not found"));

        approval.setComments(comment);
        return toDto(approvalRepo.save(approval));
    }

    private EventApprovalDto toDto(EventApprovals approval) {
        return new EventApprovalDto(
                approval.getId(),
                approval.getEvent().getId(),
                approval.getReviewer() != null ? approval.getReviewer().getUsername() : null,
                approval.getStatus().name(),
                approval.getReviewDate(),
                approval.getComments(),
                approval.getApprovalConditions()
        );
    }
}
