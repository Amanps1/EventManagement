package com.example.eventmanagement.service.user;

import com.example.eventmanagement.dto.UserDto;
import com.example.eventmanagement.model.User;
import com.example.eventmanagement.request.UserProfileUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {


    UserDto getCurrentUserProfile();

    Page<UserDto> getAllUsers(int page, int size, String filter);

    UserDto getUserById(Long id);

    void updateUserRole(Long id, String role);

    List<UserDto> getUsersByZone(Long zoneId);

    void updateUserProfile(UserProfileUpdateRequest updateRequest);

    void deactivateUser(Long id);
}
