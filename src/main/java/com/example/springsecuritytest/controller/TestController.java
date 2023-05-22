package com.example.springsecuritytest.controller;

import com.example.springsecuritytest.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;
    @GetMapping("/hello")
    public String hello() {
        return testService.hello();
    }

}
