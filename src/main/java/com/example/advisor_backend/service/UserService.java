package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.User;

public interface UserService {
    User getUserByName(String username);
}
