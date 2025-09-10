package com.example.eventmanagement.service.venue;

import com.example.eventmanagement.dto.VenueDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.model.Venue;
import com.example.eventmanagement.repository.VenueRepository;
import com.example.eventmanagement.request.VenueRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VenueService implements IVenueService{
    private final VenueRepository venueRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<VenueDto> getAllVenues(int page, int size) {
        return venueRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(venue -> modelMapper.map(venue, VenueDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public VenueDto getVenueById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + id));
        return modelMapper.map(venue, VenueDto.class);
    }

    @Override
    public VenueDto createVenue(VenueRequest request) {
        Venue venue = modelMapper.map(request, Venue.class);
        Venue saved = venueRepository.save(venue);
        return modelMapper.map(saved, VenueDto.class);
    }

    @Override
    public VenueDto updateVenue(Long id, VenueRequest request) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + id));

        modelMapper.map(request, venue); // update fields
        Venue updated = venueRepository.save(venue);
        return modelMapper.map(updated, VenueDto.class);
    }

    @Override
    public void deleteVenue(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + id));
        venueRepository.delete(venue);
    }

    @Override
    public List<Object> getAvailableVenues(String startDate, String endDate) {
        // TODO: Implement availability logic with bookings
        return List.of();
    }

    @Override
    public List<Object> getVenueBookings(Long venueId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + venueId));
        return List.copyOf(venue.getBookings());
    }

    @Override
    public List<VenueDto> getVenuesByZone(Long zoneId) {
        return venueRepository.findByZoneId(zoneId)
                .stream()
                .map(venue -> modelMapper.map(venue, VenueDto.class))
                .collect(Collectors.toList());
    }

}
