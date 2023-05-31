package com.example.springsecuritytest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserService userService;

  /**
   * Username 기반으로 User 검색
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info(" 4. UserDetailsServiceImpl loadUserByUsername");
    return UserDetailsImpl.build(userService.selectUserByName(username));
  }

}
