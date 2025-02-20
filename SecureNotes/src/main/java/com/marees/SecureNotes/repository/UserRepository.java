package com.marees.SecureNotes.repository;

import com.marees.SecureNotes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String username);

    Boolean existsByUserName(String user1);
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
