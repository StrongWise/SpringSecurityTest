package com.example.springsecuritytest.service;

import com.example.springsecuritytest.mapper.UserMapper;
import com.example.springsecuritytest.mapper.UserRoleMapper;
import com.example.springsecuritytest.model.Role;
import com.example.springsecuritytest.model.User;
import com.example.springsecuritytest.model.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description :
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    @Transactional
    public int saveUserInfo(User user) {
        int cnt = userMapper.insertUser(user);
        for (Role role : user.getRoles()) {
            userRoleMapper.insertUserRole(UserRole.builder()
                    .userId(user.getId())
                    .roleId(role.getId())
                    .build());
        }
        return cnt;
    }
}
