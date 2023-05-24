package com.example.springsecuritytest.controller;

import com.example.springsecuritytest.mapper.RoleMapper;
import com.example.springsecuritytest.model.ERole;
import com.example.springsecuritytest.model.User;
import com.example.springsecuritytest.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * description :
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

//    private final TestService testService;
    private final RoleMapper roleMapper;
    @GetMapping("/hello")
    public String hello() {
        log.info("byName > {}", roleMapper.selectRoleByName(ERole.ROLE_USER));
        log.info("byName > {}", roleMapper.selectRoleByName(ERole.ROLE_MODERATOR));
        log.info("byName > {}", roleMapper.selectRoleByName(ERole.ROLE_ADMIN));

        return "";
//        return testService.hello();
    }

}
