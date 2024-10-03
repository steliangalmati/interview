package com.interview.configuration.security;

import com.interview.service.security.AppUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AppUserDetailService appUserDetailService;

    private final CustomAuthenticationManager customAuthenticationManager;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                                .requestMatchers(
                                         "/h2-console/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/users/register",
                                        "/api/v1/login")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .addFilter(new JwtAuthorizationFilter(customAuthenticationManager, jwtUtils, appUserDetailService))
                .authenticationManager(new CustomAuthenticationManager(passwordEncoder, appUserDetailService));

        return http.build();
    }
}
