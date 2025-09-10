package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venue,Long> {
    Optional<Venue> findByVenueName(String venueName);
}
