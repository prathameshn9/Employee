package com.Employee.Management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Employee.Management.models.Role;
import com.Employee.Management.models.User;
import com.Employee.Management.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserDetailsServices implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		
		Optional<Role> role = Optional.ofNullable(user.getRole()); // Handle potentially null role
		List<GrantedAuthority> authorities = role
				.map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName().toUpperCase())).map(auth -> { // Convert to a
																											// List<GrantedAuthority>
					List<GrantedAuthority> list = new ArrayList<>();
					list.add(auth);
					return list;
				}).orElse(List.of());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities 
		);
	}
}
