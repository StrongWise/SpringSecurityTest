package com.example.springsecuritytest.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {
  private String accessToken;
  private String tokenType = "Bearer";
  private Long id;
  private String username;
  private String email;
  private List<String> roles;
}
