package com.interview.configuration.security;

import com.interview.service.security.AppUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final PasswordEncoder passwordEncoder;
    private final AppUserDetailService appUserDetailService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = appUserDetailService.loadUserByUsername((String) authentication.getPrincipal());
        if (userDetails == null) {
            return null;
        }

        if (passwordEncoder.matches((String) authentication.getCredentials(), userDetails.getPassword())) {
            return UsernamePasswordAuthenticationToken.authenticated(
                    userDetails.getUsername(),
                    authentication.getCredentials(),
                    userDetails.getAuthorities()
            );
        }

        return null;
    }
}
