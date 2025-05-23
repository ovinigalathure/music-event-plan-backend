package com.backend.music_event.services;

import com.backend.music_event.model.User;
import com.backend.music_event.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER"); // Default role
        }
        return userRepository.save(user);
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password)); // Passwords should be hashed & compared securely!
    }

    // NEW: Update user profile
    public Optional<User> updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();

        // Update allowed fields
        if (userDetails.getName() != null) user.setName(userDetails.getName());
        if (userDetails.getPhone() != null) user.setPhone(userDetails.getPhone());
        if (userDetails.getAddress() != null) user.setAddress(userDetails.getAddress());
        if (userDetails.getAvatarUrl() != null) user.setAvatarUrl(userDetails.getAvatarUrl());
        if (userDetails.getCity() != null) user.setCity(userDetails.getCity());
        if (userDetails.getState() != null) user.setState(userDetails.getState());
        if (userDetails.getZipCode() != null) user.setZipCode(userDetails.getZipCode());
        if (userDetails.getCountry() != null) user.setCountry(userDetails.getCountry());
        if (userDetails.getBirthDate() != null) user.setBirthDate(userDetails.getBirthDate());

        userRepository.save(user);
        return Optional.of(user);
    }

    // NEW: Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);  // Fetch user by ID
    }
}
