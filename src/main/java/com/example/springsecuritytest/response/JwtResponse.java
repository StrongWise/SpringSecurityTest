package com.example.springsecuritytest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
  private String accessToken;
  private String tokenType = "Bearer";
  private Long id;
  private String username;
  private String email;
  private List<String> roles;
}
