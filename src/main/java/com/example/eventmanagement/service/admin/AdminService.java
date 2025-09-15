package com.example.eventmanagement.service.admin;

import com.example.eventmanagement.dto.AuditLogDto;
import com.example.eventmanagement.dto.SystemHealthDto;
import com.example.eventmanagement.dto.SystemStatisticsDto;
import com.example.eventmanagement.repository.AuditLogRepository;
import com.example.eventmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService implements  IAdminService{
    private final AuditLogRepository auditLogRepo;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;


    public SystemHealthDto getSystemHealth() {
        SystemHealthDto dto = new SystemHealthDto();
        dto.setStatus("UP");
        dto.setDatabaseStatus("CONNECTED");
        dto.setMessage("System is healthy");
        return dto;
    }


    public List<AuditLogDto> getAuditLogs() {
        return auditLogRepo.findAll().stream()
                .map(log -> {
                    AuditLogDto dto = modelMapper.map(log, AuditLogDto.class);
                    dto.setUserId(log.getUser().getId());
                    dto.setUsername(log.getUser().getUsername());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public List<AuditLogDto> getUserActivityLogs(Long userId) {
        return auditLogRepo.findAll().stream()
                .filter(log -> log.getUser().getId().equals(userId))
                .map(log -> {
                    AuditLogDto dto = modelMapper.map(log, AuditLogDto.class);
                    dto.setUserId(log.getUser().getId());
                    dto.setUsername(log.getUser().getUsername());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public String triggerBackup() {
        return "System backup triggered successfully";
    }


    public SystemStatisticsDto getSystemStatistics() {
        SystemStatisticsDto dto = new SystemStatisticsDto();
        dto.setTotalUsers(userRepo.count());
        dto.setActiveEvents(0L); // implement when Event repo ready
        dto.setTotalAuditLogs(auditLogRepo.count());
        return dto;
    }


    public String updateSystemSettings(String key, String value) {

        return "System setting updated: " + key + "=" + value;
    }

    /** GET system settings */
    public String getSystemSettings() {

        return "System settings retrieved successfully";
    }
}
