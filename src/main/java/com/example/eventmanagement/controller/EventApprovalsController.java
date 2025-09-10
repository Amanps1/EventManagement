package com.example.eventmanagement.controller;

import com.example.eventmanagement.dto.EventApprovalDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.service.eventapproval.IEventApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class EventApprovalsController {
    private final IEventApprovalService approvalService;

    @PostMapping("/events/{eventId}/submit-for-approval")
    public ResponseEntity<ApiResponse> submitForApproval(@PathVariable Long eventId) {
        try {
            EventApprovalDto dto = approvalService.submitForApproval(eventId);
            return ResponseEntity.ok(new ApiResponse("Event Submitted for Approval", dto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/approvals/pending")
    public ResponseEntity<ApiResponse> getPendingApprovals() {
        try {
            List<EventApprovalDto> pending = approvalService.getPendingApprovals();
            return ResponseEntity.ok(new ApiResponse("Success", pending));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/approvals/{approvalId}")
    public ResponseEntity<ApiResponse> updateApprovalStatus(
            @PathVariable Long approvalId,
            @RequestParam String status,
            @RequestParam(required = false) String comments) {
        try {
            EventApprovalDto updated = approvalService.updateApprovalStatus(approvalId, status, comments);
            return ResponseEntity.ok(new ApiResponse("Status Updated Successfully", updated));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/approvals/history")
    public ResponseEntity<ApiResponse> getApprovalHistory() {
        try {
            List<EventApprovalDto> history = approvalService.getApprovalHistory();
            return ResponseEntity.ok(new ApiResponse("Success", history));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/approvals/{approvalId}/comments")
    public ResponseEntity<ApiResponse> addApprovalComment(
            @PathVariable Long approvalId,
            @RequestParam String comment) {
        try {
            EventApprovalDto dto = approvalService.addApprovalComment(approvalId, comment);
            return ResponseEntity.ok(new ApiResponse("Comment Added", dto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
