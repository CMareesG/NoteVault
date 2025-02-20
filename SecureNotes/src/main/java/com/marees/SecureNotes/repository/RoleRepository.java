package com.marees.SecureNotes.repository;

import com.marees.SecureNotes.models.AppRole;
import com.marees.SecureNotes.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(AppRole approle);
}
