package com.example.springsecuritytest.model;

import lombok.Builder;
import lombok.Data;

/**
 * description :
 */
@Data
@Builder
public class UserRole {
    private Long userId;
    private Long roleId;
}
