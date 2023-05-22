package com.example.springsecuritytest.service;

import com.example.springsecuritytest.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * description :
 */
@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    public String hello() {
        return testRepository.hello();
    }
}
