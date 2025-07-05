package com.example.advisor_backend.Repository;

import com.example.advisor_backend.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String username);
}

