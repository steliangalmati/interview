package com.interview.service.security;

import com.interview.database.model.DbUser;
import com.interview.database.repository.UserRepository;
import com.interview.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Optional;

@Validated
@Service
@RequiredArgsConstructor
public class AppUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<DbUser> userOptional = userRepository.findDbUserByUserName(username);
        if (!userOptional.isPresent()) {
            throw new NotFoundException("User not found!");
        }

        DbUser user = userOptional.get();
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
    }
}
