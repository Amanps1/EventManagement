package com.example.eventmanagement.service.user;

import com.example.eventmanagement.dto.UserDto;
import com.example.eventmanagement.exceptions.ResourceNotFoundException;
import com.example.eventmanagement.model.Role;
import com.example.eventmanagement.model.User;
import com.example.eventmanagement.repository.RoleRepository;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.request.UserProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public UserDto getCurrentUserProfile() {
        Long userId = 1L;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return convertUserToDto(user);
    }


    @Override
    public Page<UserDto> getAllUsers(int page, int size, String filter) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> users;

        if (filter != null && !filter.isEmpty()) {
            users = userRepository.findAll(pageRequest)
                    .map(u -> u)
                    .map(user -> user);
        } else {
            users = userRepository.findAll(pageRequest);
        }

        List<UserDto> userDtos = users.getContent()
                .stream()
                .map(this::convertUserToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(userDtos, pageRequest, users.getTotalElements());
    }


    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertUserToDto(user);
    }


    @Override
    public void updateUserRole(Long id, String roleName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        Role role = roleRepository.findByName(User.RoleEnum.valueOf(roleName))
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName));

        user.setRole(role);
        userRepository.save(user);
    }


    @Override
    public List<UserDto> getUsersByZone(Long zoneId) {
        List<User> users = userRepository.findByZoneId(zoneId);
        return users.stream()
                .map(this::convertUserToDto)
                .collect(Collectors.toList());
    }


    @Override
    public void updateUserProfile(UserProfileUpdateRequest updateRequest) {
        Long userId = 1L; // TODO: get from authentication context
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (updateRequest.getFirstName() != null) user.setFirstName(updateRequest.getFirstName());
        if (updateRequest.getLastName() != null) user.setLastName(updateRequest.getLastName());
        if (updateRequest.getAddress() != null) user.setAddress(updateRequest.getAddress());
        if (updateRequest.getPhoneNumber() != null) user.setPhoneNumber(updateRequest.getPhoneNumber());
        if (updateRequest.getEmergencyContact() != null) user.setEmergencyContact(updateRequest.getEmergencyContact());

        userRepository.save(user);
    }
    @Override
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setActive(false);
        userRepository.save(user);
    }

    private UserDto convertUserToDto(User user) {
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
        dto.setRole(user.getRole() != null ? User.RoleEnum.valueOf(String.valueOf(user.getRole().getName())) : null);
        dto.setZoneId(user.getZone() != null ? user.getZone().getId() : null);
        dto.setOrganizedEventIds(user.getOrganizedEvents().stream().map(e -> e.getId()).collect(Collectors.toList()));
        dto.setApprovedEventIds(user.getApprovedEvents().stream().map(e -> e.getId()).collect(Collectors.toList()));
        dto.setRegisteredEventIds(user.getEventRegistrations().stream().map(r -> r.getId()).collect(Collectors.toList()));
        dto.setCoordinatedZoneIds(user.getCoordinatedZones().stream().map(z -> z.getId()).collect(Collectors.toList()));
        dto.setReviewIds(user.getReviews().stream().map(r -> r.getId()).collect(Collectors.toList()));
        dto.setReceivedNotificationIds(user.getReceivedNotifications().stream().map(n -> n.getId()).collect(Collectors.toList()));
        dto.setSentNotificationIds(user.getSentNotifications().stream().map(n -> n.getId()).collect(Collectors.toList()));
        dto.setBookingIds(user.getBookings().stream().map(b -> b.getId()).collect(Collectors.toList()));
        dto.setAuditLogIds(user.getAuditLogs().stream().map(a -> a.getId()).collect(Collectors.toList()));

        return dto;
    }
}
