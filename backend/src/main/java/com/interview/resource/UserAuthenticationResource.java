package com.interview.resource;

import com.interview.service.security.UserLoginService;
import com.interview.service.model.auth.UserLoginDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/login")
public class UserAuthenticationResource {

    private final UserLoginService userLoginService;

    @PostMapping
    private Map<String, String> userLogin(@RequestBody UserLoginDao userLoginDao) {
        return userLoginService.loginUser(userLoginDao);


    }
}
