package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.repository.UserRepository;
import com.example.advisor_backend.model.entity.User;
import com.example.advisor_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByName(String username) {
        // 根据用户ID查询关联策略
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}