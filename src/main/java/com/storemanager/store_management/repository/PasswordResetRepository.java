package com.storemanager.store_management.repository;

import com.storemanager.store_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

