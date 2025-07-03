// src/main/java/com/example/advisor_backend/repository/UserRepository.java
package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
}
