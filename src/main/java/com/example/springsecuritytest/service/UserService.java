package com.example.springsecuritytest.service;

import com.example.springsecuritytest.mapper.RoleMapper;
import com.example.springsecuritytest.mapper.UserMapper;
import com.example.springsecuritytest.model.Role;
import com.example.springsecuritytest.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * description :
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    public int saveUserInfo(User user) {
        userMapper.insertUser(user);
        for (Role role : user.getRoles()) {
            roleMapper.insertRole(role);
        }
        return 0;
    }
}
