package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration,Long> {
    List<EventRegistration> findByEventId(Long eventId);

    Optional<EventRegistration> findByEventIdAndUserId(Long eventId, Long userId);

    List<EventRegistration>findByEventIdAndStatus(Long eventId, EventRegistration.Status status);
}
