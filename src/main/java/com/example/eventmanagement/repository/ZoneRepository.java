package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone,Long> {
    Optional<Zone> findById(Long zoneId);
}
