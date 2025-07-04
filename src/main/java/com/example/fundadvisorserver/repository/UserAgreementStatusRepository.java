package com.example.fundadvisorserver.repository;

import com.example.fundadvisorserver.entity.UserAgreementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserAgreementStatusRepository extends JpaRepository<UserAgreementStatus, Long> {
    Optional<UserAgreementStatus> findByUserId(Long userId);
} 