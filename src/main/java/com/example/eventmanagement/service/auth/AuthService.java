package com.example.eventmanagement.service.auth;

import com.example.eventmanagement.dto.LoginDto;
import com.example.eventmanagement.dto.LoginResponse;
import com.example.eventmanagement.dto.RegistrationDto;
import com.example.eventmanagement.dto.UserDto;
import com.example.eventmanagement.model.Role;
import com.example.eventmanagement.model.User;
import com.example.eventmanagement.repository.RoleRepository;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.repository.ZoneRepository;
import com.example.eventmanagement.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final ZoneRepository zoneRepo;
    private final RoleRepository roleRepository;

    @Override
    public ApiResponse register(RegistrationDto registrationDto) {
        if (userRepo.findByEmail(registrationDto.getEmail()).isPresent()) {
            return new ApiResponse("Email already exists", null);
        }
        if (userRepo.findByUsernameIgnoreCase(registrationDto.getUsername()).isPresent()) {
            return new ApiResponse("Username already exists", null);
        }

        Role role = roleRepository.findByName(registrationDto.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found: " + registrationDto.getRole()));

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setAddress(registrationDto.getAddress());
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setEmergencyContact(registrationDto.getEmergencyContact());
        user.setRole(role);
        user.setActive(true);

        userRepo.save(user);

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return new ApiResponse("Registration successful", userDto);
    }

    @Override
    public ApiResponse login(LoginDto loginDto) {
        User user = userRepo.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
            return new ApiResponse("Invalid credentials", null);
        }

        UserDto userDto = modelMapper.map(user, UserDto.class);

        return new ApiResponse("Login successful", new LoginResponse(userDto, null));
    }


    @Override
    public ApiResponse refreshToken(String refreshToken) {
        // ❌ No refresh token logic, just acknowledge
        return new ApiResponse("Refresh not required (no JWT used)", null);
    }

    @Override
    public ApiResponse sendPasswordReset(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resetLink = "demo-reset-link"; // TODO: replace with email logic
        return new ApiResponse("Password reset link sent", resetLink);
    }

    @Override
    public ApiResponse resetPassword(String token, String newPassword) {
        // ❌ Token ignored since no JWT
        User user = userRepo.findByEmail(token) // using token as email for simplicity
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        return new ApiResponse("Password reset successful", true);
    }

    @Override
    public ApiResponse verifyEmail(String token) {

        User user = userRepo.findByEmail(token)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmailVerified(true);
        userRepo.save(user);

        return new ApiResponse("Email verified", true);
    }

    @Override
    public ApiResponse logout(String token) {
        return new ApiResponse("Logout successful", true);
    }

}
