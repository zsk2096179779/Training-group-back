package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.UserRepository;
import com.example.advisor_backend.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServe {
    @Autowired
    private UserRepository userRepository;

    public User getUserByName(String username) {
        // 根据用户ID查询关联策略
        return userRepository.findByName(username);
    }
}
