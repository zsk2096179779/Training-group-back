package com.example.advisor_backend.service.ServiceImpl;
import com.example.advisor_backend.model.entity.User;
import com.example.advisor_backend.repository.UserRepository;
import com.example.advisor_backend.service.CustomUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepo;

    public CustomUserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在：" + username));

        // 根据角色字段生成 GrantedAuthority 列表
        List<GrantedAuthority> auths = List.of(
                new SimpleGrantedAuthority(u.getRole().name())
        );

        // 返回 Spring Security 自带的 UserDetails 实现
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                auths
        );
    }
}
