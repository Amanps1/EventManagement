package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.EventApprovals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventApprovalsRepository extends JpaRepository<EventApprovals,Long> {
    List<EventApprovals> findByStatus(EventApprovals.Status status);
    List<EventApprovals> findByEventId(Long eventId);

}
