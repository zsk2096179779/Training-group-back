package com.example.fundadvisorserver.repository;

import com.example.fundadvisorserver.entity.KycQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KycQuestionnaireRepository extends JpaRepository<KycQuestionnaire, Long> {
    Optional<KycQuestionnaire> findByUserId(Long userId);
} 