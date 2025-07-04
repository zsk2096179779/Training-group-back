package com.example.fundadvisorserver.repository;

import com.example.fundadvisorserver.entity.UserOtcStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserOtcStatusRepository extends JpaRepository<UserOtcStatus, Long> {
    Optional<UserOtcStatus> findByUserId(Long userId);
} 