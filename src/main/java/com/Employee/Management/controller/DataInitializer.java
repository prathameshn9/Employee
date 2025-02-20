package com.Employee.Management.controller;

import com.Employee.Management.models.Role;
import com.Employee.Management.models.User;
import com.Employee.Management.repo.RoleRepository;
import com.Employee.Management.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Check if roles exist, else create them
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

            Role userRole = roleRepository.findByName("USER")
                    .orElseGet(() -> roleRepository.save(new Role("USER")));

            // List of users
            List<User> users = Arrays.asList(
                    new User("admin", passwordEncoder.encode("admin123"), adminRole),
                    new User("user1", passwordEncoder.encode("user123"), userRole),
                    new User("user2", passwordEncoder.encode("user123"), userRole),
                    new User("manager", passwordEncoder.encode("manager123"), adminRole),
                    new User("employee1", passwordEncoder.encode("emp123"), userRole),
                    new User("employee2", passwordEncoder.encode("emp123"), userRole)
            );

            // Save users if not exist
            users.forEach(user -> {
                if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
                    userRepository.save(user);
                }
            });
        };
    }
}
