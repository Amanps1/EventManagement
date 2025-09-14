package com.example.eventmanagement.service.venue;

import com.example.eventmanagement.dto.VenueDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.model.ResourceBooking;
import com.example.eventmanagement.model.Venue;
import com.example.eventmanagement.model.Zone;
import com.example.eventmanagement.repository.VenueRepository;
import com.example.eventmanagement.repository.ZoneRepository;
import com.example.eventmanagement.request.VenueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenueService implements IVenueService {

    private final VenueRepository venueRepository;
    private final ZoneRepository zoneRepository;

    @Override
    public List<VenueDto> getAllVenues(int page, int size) {
        return venueRepository.findAll(PageRequest.of(page, size))
                .getContent()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VenueDto getVenueById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + id));
        return convertToDto(venue);
    }

    @Override
    public VenueDto createVenue(VenueRequest request) {
        Venue venue = new Venue();
        venue.setVenueName(request.getVenueName());
        venue.setAddress(request.getAddress());
        venue.setCapacity(request.getCapacity());
        venue.setDescription(request.getDescription());
        venue.setAmenities(request.getAmenities());
        venue.setHourlyRate(request.getHourlyRate());
        venue.setAvailable(request.isAvailable());
        venue.setContactPhone(request.getContactPhone());
        venue.setAccessibilityFeatures(request.getAccessibilityFeatures());

        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + request.getZoneId()));
        venue.setZone(zone);

        Venue saved = venueRepository.save(venue);
        return convertToDto(saved);
    }

    @Override
    public VenueDto updateVenue(Long id, VenueRequest request) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + id));

        venue.setVenueName(request.getVenueName());
        venue.setAddress(request.getAddress());
        venue.setCapacity(request.getCapacity());
        venue.setDescription(request.getDescription());
        venue.setAmenities(request.getAmenities());
        venue.setHourlyRate(request.getHourlyRate());
        venue.setAvailable(request.isAvailable());
        venue.setContactPhone(request.getContactPhone());
        venue.setAccessibilityFeatures(request.getAccessibilityFeatures());

        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + request.getZoneId()));
        venue.setZone(zone);

        Venue updated = venueRepository.save(venue);
        return convertToDto(updated);
    }

    @Override
    public void deleteVenue(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + id));
        venueRepository.delete(venue);
    }

    @Override
    public List<VenueDto> getAvailableVenues(String startDate, String endDate) {
//        LocalDateTime start = LocalDateTime.parse(startDate);
//        LocalDateTime end = LocalDateTime.parse(endDate);
//
//        return venueRepository.findAll().stream()
//                .filter(Venue::isAvailable)
//                .filter(venue -> venue.getBookings().stream()
//                        .noneMatch(booking ->
//                                !booking.getEndDate().isBefore(start) &&
//                                        !booking.getStartDate().isAfter(end)
//                        ))
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public List<VenueDto> getVenueBookings(Long id) {
        return null;
    }

    @Override
    public List<VenueDto> getVenuesByZone(Long zoneId) {
        return venueRepository.findByZoneId(zoneId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ---------------- DTO conversion ----------------
    private VenueDto convertToDto(Venue venue) {
        VenueDto dto = new VenueDto();
        dto.setId(venue.getId());
        dto.setVenueName(venue.getVenueName());
        dto.setAddress(venue.getAddress());
        dto.setCapacity(venue.getCapacity());
        dto.setDescription(venue.getDescription());
        dto.setAmenities(venue.getAmenities());
        dto.setHourlyRate(venue.getHourlyRate());
        dto.setAvailable(venue.isAvailable());
        dto.setContactPhone(venue.getContactPhone());
        dto.setAccessibilityFeatures(venue.getAccessibilityFeatures());

        if (venue.getZone() != null) {
            dto.setZoneId(venue.getZone().getId());
            dto.setZoneName(venue.getZone().getZoneName());
        }
        return dto;
    }
}
