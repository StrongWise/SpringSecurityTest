package com.example.springsecuritytest.service;

import com.example.springsecuritytest.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * description :
 */
@Service
@RequiredArgsConstructor
public class TestService {
    private final TestMapper testMapper;

    public String hello() {
        return testMapper.hello();
    }
}
