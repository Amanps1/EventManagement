package com.example.eventmanagement.controller;


import com.example.eventmanagement.dto.AuditLogDto;
import com.example.eventmanagement.dto.SystemHealthDto;
import com.example.eventmanagement.dto.SystemStatisticsDto;
import com.example.eventmanagement.request.SystemSettingsRequestDto;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.service.admin.IAdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
    private final IAdminService adminService;


    @GetMapping("/system-health")
    public ResponseEntity<ApiResponse> getSystemHealth() {
        SystemHealthDto dto = adminService.getSystemHealth();
        return ResponseEntity.ok(new ApiResponse("System health retrieved", dto));
    }


    @GetMapping("/audit-logs")
    public ResponseEntity<ApiResponse> getAuditLogs() {
        List<AuditLogDto> logs = adminService.getAuditLogs();
        return ResponseEntity.ok(new ApiResponse("Audit logs retrieved", logs));
    }

    /** GET /api/admin/user-activity */
    @GetMapping("/user-activity")
    public ResponseEntity<ApiResponse> getUserActivity(@RequestParam Long userId) {
        List<AuditLogDto> logs = adminService.getUserActivityLogs(userId);
        return ResponseEntity.ok(new ApiResponse("User activity logs retrieved", logs));
    }

    /** POST /api/admin/backup */
    @PostMapping("/backup")
    public ResponseEntity<ApiResponse> triggerBackup() {
        String result = adminService.triggerBackup();
        return ResponseEntity.ok(new ApiResponse("Backup triggered", result));
    }

    /** GET /api/admin/statistics */
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse> getSystemStatistics() {
        SystemStatisticsDto stats = adminService.getSystemStatistics();
        return ResponseEntity.ok(new ApiResponse("System statistics retrieved", stats));
    }

    /** PUT /api/admin/settings */
    @PutMapping("/settings")
    public ResponseEntity<ApiResponse> updateSystemSettings(@RequestBody SystemSettingsRequestDto request) {
        String result = adminService.updateSystemSettings(request.getKey(), request.getValue());
        return ResponseEntity.ok(new ApiResponse("System settings updated", result));
    }

    /** GET /api/admin/settings */
    @GetMapping("/settings")
    public ResponseEntity<ApiResponse> getSystemSettings() {
        String result = adminService.getSystemSettings();
        return ResponseEntity.ok(new ApiResponse("System settings retrieved", result));
    }
}
