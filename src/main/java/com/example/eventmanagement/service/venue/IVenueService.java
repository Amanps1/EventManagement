package com.example.eventmanagement.service.venue;

import com.example.eventmanagement.dto.VenueDto;
import com.example.eventmanagement.request.VenueRequest;

import java.util.List;

public interface IVenueService {
    List<VenueDto> getAllVenues(int page, int size);

    VenueDto getVenueById(Long id);

    VenueDto createVenue(VenueRequest venueRequest);

    VenueDto updateVenue(Long id, VenueRequest venueRequest);

    void deleteVenue(Long id);

    Object getAvailableVenues(String startDate, String endDate);

    Object getVenueBookings(Long id);

    Object getVenuesByZone(Long zoneId);
}
