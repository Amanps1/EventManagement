package com.example.eventmanagement.security;

import com.example.eventmanagement.model.User;
import com.example.eventmanagement.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        // input can be username OR email
        User user = userRepository.findByUsername(input)
                .or(() -> userRepository.findByEmail(input))   // 👈 fallback
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + input));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())  // store email as principal
                .password(user.getPasswordHash())
                .roles(user.getRole().name())   // Spring automatically adds ROLE_
                .build();
    }
}
