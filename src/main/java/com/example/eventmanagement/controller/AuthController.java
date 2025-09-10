package com.example.eventmanagement.controller;

import com.example.eventmanagement.dto.LoginDto;
import com.example.eventmanagement.dto.LoginResponse;
import com.example.eventmanagement.dto.RegistrationDto;
import com.example.eventmanagement.request.ForgotPasswordRequest;
import com.example.eventmanagement.request.PasswordResetRequest;
import com.example.eventmanagement.request.RefreshTokenRequest;
import com.example.eventmanagement.response.ApiResponse;
import com.example.eventmanagement.service.auth.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final IAuthService authService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationDto registrationDto) {
        ApiResponse response = authService.register(registrationDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto) {
        ApiResponse response = authService.login(loginDto);
        LoginResponse loginResponse = (LoginResponse) response.getData();
        System.out.println(loginResponse.getToken());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + loginResponse.getToken());
        return new ResponseEntity<>(response, headers, HttpStatusCode.valueOf(200)); // Data field contains UserDto or error details
    }

    public ResponseEntity<ApiResponse> logout(@RequestHeader("Authorization") String authHeader) {
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        ApiResponse response = authService.logout(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refresh(@RequestBody RefreshTokenRequest tokenRequest) {
        ApiResponse response = authService.refreshToken(tokenRequest.getRefreshToken());
        return ResponseEntity.ok(response); // Data could be JWT or refresh status
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        ApiResponse response = authService.sendPasswordReset(request.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody PasswordResetRequest request) {
        ApiResponse response = authService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-email/{token}")
    public ResponseEntity<ApiResponse> verifyEmail(@PathVariable String token) {
        ApiResponse response = authService.verifyEmail(token);
        return ResponseEntity.ok(response);
    }
}
