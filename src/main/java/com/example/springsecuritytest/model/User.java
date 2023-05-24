package com.example.springsecuritytest.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * description :
 */
@Data
@Builder
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;

    private Set<Role> roles = new HashSet<>();
}
