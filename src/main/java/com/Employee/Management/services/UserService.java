package com.Employee.Management.services;

import com.Employee.Management.models.Role;
import com.Employee.Management.models.User;
import com.Employee.Management.repo.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User authenticate(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public User registerUser(String username, String password, String roleName) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists!");
        }
        User user = new User(username, passwordEncoder.encode(password), new Role(roleName));
        return userRepository.save(user);
    }
}
