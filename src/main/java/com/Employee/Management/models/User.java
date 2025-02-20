package com.Employee.Management.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER) // Fetch eagerly to avoid LazyInitializationException
    @JoinColumn(name = "role_id", nullable = false)
    private Role roles;

  
    public User() {}

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.roles = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (roles != null && roles.getName() != null) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roles.getName().toUpperCase()));
        }
        return Collections.emptyList(); 
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public Role getRole() {
    	return roles;
    }
    
}