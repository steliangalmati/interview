package com.interview.service.security;

import com.interview.configuration.security.JwtUtils;
import com.interview.service.model.auth.UserLoginDao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public Map<String, String> loginUser(@Valid @NotNull UserLoginDao userLoginDao) {
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDao.getUserName(), userLoginDao.getPassword()));

            if (authentication == null) {
                throw new BadCredentialsException("Invalid username or password");
            }
            String token = jwtUtils.generateToken(authentication.getName());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
    }
}
