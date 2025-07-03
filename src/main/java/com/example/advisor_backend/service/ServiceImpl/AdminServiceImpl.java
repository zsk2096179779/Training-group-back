// src/main/java/com/example/advisor_backend/service/impl/AdminServiceImpl.java
package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.model.entity.User;
import com.example.advisor_backend.model.dto.UserResponse;
import com.example.advisor_backend.repository.UserRepository;
import com.example.advisor_backend.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepo;

    public AdminServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserResponse> listAllUsers() {
        return userRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private UserResponse toDto(User u) {
        return new UserResponse(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getRole().name(),
                u.getCreatedAt(),
                u.getUpdatedAt()
        );
    }
}
