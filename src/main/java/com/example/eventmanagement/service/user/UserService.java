package com.example.eventmanagement.service.user;

import com.example.eventmanagement.dto.UserDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.Notification;
import com.example.eventmanagement.model.ResourceBooking;
import com.example.eventmanagement.model.AuditLog;
import com.example.eventmanagement.model.User;
import com.example.eventmanagement.model.Zone;
import com.example.eventmanagement.model.EventApprovals;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.request.UserProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    // ✅ Get the current logged-in user profile
    @Override
    public UserDto getCurrentUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principal = auth.getName(); // username or email from JWT

        User user = userRepo.findByUsernameIgnoreCaseOrEmailIgnoreCase(principal, principal)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found"));

        return convertUserToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers(int page, int size, String filter) {
        Pageable pageable = PageRequest.of(page, size);
        List<User> users = userRepo.findAll(pageable).getContent();
        return users.stream().map(this::convertUserToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertUserToDto(user);
    }

    @Override
    public void updateUserRole(Long id, String role) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        try {
            User.Role roleEnum = User.Role.valueOf(role.toUpperCase());
            user.setRole(roleEnum);
            userRepo.save(user);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + role);
        }
    }

    @Override
    public void deactivateUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setActive(false);
        userRepo.save(user);
    }

    @Override
    public List<UserDto> getUsersByZone(Long zoneId) {
        List<User> users = userRepo.findByZoneId(zoneId);
        return users.stream().map(this::convertUserToDto).collect(Collectors.toList());
    }

    @Override
    public void updateUserProfile(UserProfileUpdateRequest updateRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principal = auth.getName();

        User user = userRepo.findByUsernameIgnoreCaseOrEmailIgnoreCase(principal, principal)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found"));

        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setAddress(updateRequest.getAddress());
        user.setPhoneNumber(updateRequest.getPhoneNumber());
        user.setEmergencyContact(updateRequest.getEmergencyContact());

        userRepo.save(user);
    }

    // ✅ Convert User entity to UserDto
    public UserDto convertUserToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAddress(user.getAddress());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmergencyContact(user.getEmergencyContact());
        dto.setActive(user.isActive());
        dto.setEmailVerified(user.isEmailVerified());
        dto.setCreatedDate(user.getCreatedDate());
        dto.setLastLogin(user.getLastLogin());
        dto.setRole(user.getRole());
        dto.setZoneId(user.getZone() != null ? user.getZone().getId() : null);

        dto.setOrganizedEventIds(user.getOrganizedEvents().stream().map(Event::getId).toList());
        dto.setApprovedEventIds(user.getApprovedEvents().stream().map(Event::getId).toList());
        dto.setRegisteredEventIds(user.getEventRegistrations().stream().map(er -> er.getEvent().getId()).toList());
        dto.setCoordinatedZoneIds(user.getCoordinatedZones().stream().map(Zone::getId).toList());
        dto.setReviewIds(user.getReviews().stream().map(EventApprovals::getId).toList());
        dto.setReceivedNotificationIds(user.getReceivedNotifications().stream().map(Notification::getId).toList());
        dto.setSentNotificationIds(user.getSentNotifications().stream().map(Notification::getId).toList());
        dto.setBookingIds(user.getBookings().stream().map(ResourceBooking::getId).toList());
        dto.setAuditLogIds(user.getAuditLogs().stream().map(AuditLog::getId).toList());

        return dto;
    }
}
