package com.example.eventmanagement.controller;

import com.example.eventmanagement.dto.UserDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.request.UserProfileUpdateRequest;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    @Autowired
    public IUserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getCurrentUserProfile(){
        try {
            UserDto userdto=userService.getCurrentUserProfile();
            return ResponseEntity.ok(new ApiResponse("Success", userdto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filter) {

        Page<UserDto> userPage = userService.getAllUsers(page, size, filter);

        Map<String, Object> response = new HashMap<>();
        response.put("users", userPage.getContent());
        response.put("currentPage", userPage.getNumber());
        response.put("totalItems", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());

        return ResponseEntity.ok(new ApiResponse("Success", response));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id){
        try{
            UserDto user=userService.getUserById(id);
            return ResponseEntity.ok(new ApiResponse("Success",user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponse> updateUserRole(
            @PathVariable Long id,
            @RequestParam String role
    ) {
        try {
            userService.updateUserRole(id, role);
            return ResponseEntity.ok(new ApiResponse("Role updated successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deactivateUser(@PathVariable Long id) {
        try {
            userService.deactivateUser(id);
            return ResponseEntity.ok(new ApiResponse("User deactivated successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<ApiResponse> getUsersByZone(@PathVariable Long zoneId) {
        List<UserDto> users = userService.getUsersByZone(zoneId);
        return ResponseEntity.ok(new ApiResponse("Success", users));
    }
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateUserProfile(@RequestBody UserProfileUpdateRequest updateRequest) {
        try {
            userService.updateUserProfile(updateRequest);
            return ResponseEntity.ok(new ApiResponse("Profile updated successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
