package com.example.eventmanagement.service.zones;

import com.example.eventmanagement.dto.ZoneDto;
import com.example.eventmanagement.request.ZoneRequest;

import java.util.List;

public interface IZoneService {
    List<ZoneDto> getAllZones();

    ZoneDto getZoneById(Long id);

    ZoneDto createZone(ZoneRequest zoneRequest);

    ZoneDto updateZone(Long id, ZoneRequest zoneRequest);

    void deleteZone(Long id);

    Object getZoneEvents(Long id);

    Object getZoneResidents(Long id);
}
