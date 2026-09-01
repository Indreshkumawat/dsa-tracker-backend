package com.example.DSATracker.repository;


import com.example.DSATracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Used by Google OAuth2 Handler, JwtAuthFilter, and UserProgressService
    Optional<User> findByEmail(String email);

    // Helper method to check if a user already exists by email
    boolean existsByEmail(String email);
}