// src/main/java/com/example/advisor_backend/repository/AnnouncementRepository.java
package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findRecentByFund_Code(String code);
}
