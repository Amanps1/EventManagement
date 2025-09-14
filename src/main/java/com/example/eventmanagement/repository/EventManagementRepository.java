package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EventManagementRepository extends JpaRepository<Event,Long> {


    List<Event> findByCategory(Event.Category category);

    @Query("SELECT e FROM Event e WHERE e.venue.zone.id = :zoneId")
    List<Event>findByZone(Long zoneId);



    @Query("SELECT e FROM Event e WHERE " +
            "(:keyword IS NULL OR LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:category IS NULL OR e.category = :category)")
    List<Event>searchEvents(String keyword, Event.Category cat);

    List<Event>findByStartDatetimeAfter(LocalDateTime now);

    @Query("SELECT e FROM Event e WHERE e.startDatetime BETWEEN :start AND :end")
    List<Event>findEventsInRange(LocalDateTime start, LocalDateTime end);
    @Query("SELECT r.event FROM EventRegistration r WHERE r.user.username = :username")
    List<Event>findMyRegistrations(String username);

    @Query("SELECT e FROM Event e WHERE e.organizer.username = :username")
    List<Event>findByOrganizerUsername(String username);
}
