package com.example.springsecuritytest.config;

import com.example.springsecuritytest.jwt.AuthEntryPointJwt;
import com.example.springsecuritytest.jwt.AuthTokenFilter;
import com.example.springsecuritytest.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * description :
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 시큐리티 필터를 스프링 필터체인에 등록
public class WebSecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * 인증 공급자 객체
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // UserDetailsService 를 통해 인증 공급
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * 인증 매니저 객체
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * 비밀번호 암호화 객체
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * <pre>
     * CORS(Cross-Origin Resource Sharing) 세부 설정
     * - setAllowedOrigins : 교차 출처 요청에 대한 허용 목록 설정
     * - setAllowedMethods : 허용할 HTTP 메서드 설정
     * - setAllowedHeaders : 사전요청(pre-flight request)에서 실제 요청 중에 허용되는 헤더 목록 설정
     * - setAllowedCredentials : 사용자 자격 증명 지원 여부 설정
     * </pre>
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration conf = new CorsConfiguration();
        conf.setAllowedOrigins(List.of("http://localhost:3000"));
        conf.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS"));
        conf.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        conf.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", conf);
        return source;
    }
    /**
     * <pre>
     * HttpSecurity filter config
     * - 리소스(URL) 접근 권한 설정
     * - 커스텀 로그인 페이지 지원
     * - 사용자 로그아웃
     * - CSRF 공격으로 부터 보호
     * </pre>
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource())
                // CSRF(Cross site Request forgery) 사이트간 위조 요청 설정 disable
                // rest api 서버는 session 기반 인증과 다르게 stateless 하기에 서버에 인증정보를 보관하지 않는다
                .and().csrf().disable()
                // 인증 실패시 AuthEntryPointJwt 에서 처리
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 인가 실패시 AccessDeniedHandler 에서 처리
                //.accessDeniedHandler(AccessDeniedHandler handle 재정의)
                // 세션 관리 정책 설정 rest api 서버라 무상태(STATELESS)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 리소스 접근 권한 설정
                .authorizeHttpRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/board/**").permitAll()
                .anyRequest().authenticated();

        // 인증 공급자 설정
        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
     * 요청을 무시하도록 구성 하는 경우 쓰지만 permitAll 을 쓰는게 좋다
     */
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
//    }
}

