package com.example.springsecuritytest.controller;

import com.example.springsecuritytest.jwt.JwtUtils;
import com.example.springsecuritytest.mapper.RoleMapper;
import com.example.springsecuritytest.mapper.UserMapper;
import com.example.springsecuritytest.model.ERole;
import com.example.springsecuritytest.model.Role;
import com.example.springsecuritytest.model.User;
import com.example.springsecuritytest.request.LoginRequest;
import com.example.springsecuritytest.request.SignupRequest;
import com.example.springsecuritytest.response.JwtResponse;
import com.example.springsecuritytest.response.MessageResponse;
import com.example.springsecuritytest.service.UserDetailsImpl;
import com.example.springsecuritytest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * description :
 */
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    // 인증 매니저
    private final AuthenticationManager authenticationManager;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    /**
     * 사용자 로그인
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("AuthController /signin");

        // Username, password 로 인증 토큰 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        // 인증 토큰으로 인증 정보 생성
        Authentication authentication = authenticationManager.authenticate(token);

        // SecurityContext 에 인증 정보 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인증 정보로 jwt 토큰 생성
        String jwt = jwtUtils.generateJwtToken(authentication);

        // 인증정보에서 사용자 정보 조회
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        // 사용자 권한 조회
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // jwt accessToken 응답
        return ResponseEntity.ok(JwtResponse.builder()
                .accessToken(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build());
    }

    /**
     * 사용자 등록
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        log.info("AuthController /signup");
        // username 중복체크
        if (userMapper.isExistUser(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // email 중복체크
        if (userMapper.isExistEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // 새 계정 생성
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .roles(createRoles(signUpRequest.getRole()))
                .build();

        // 새 계정 저장
        userService.saveUserInfo(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * 권한 생성
     */
    private Set<Role> createRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleMapper.selectRoleByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleMapper.selectRoleByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleMapper.selectRoleByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleMapper.selectRoleByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }

    /**
     * 권한 조회
     */
    @GetMapping("/roles")
    public ResponseEntity<?> createRoles() {
        log.info("AuthController /roles");
        return ResponseEntity.ok(roleMapper.selectRoles());
    }

}
