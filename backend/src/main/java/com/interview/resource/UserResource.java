package com.interview.resource;

import com.interview.service.UserService;
import com.interview.service.model.user.CreateUserDao;
import com.interview.service.model.PagedResponse;
import com.interview.service.model.user.UserDao;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserResource {

    private final UserService userService;

    @GetMapping
    public PagedResponse<UserDao> getUsers(@RequestParam(defaultValue = "0") int page,
                                           @Max(500) @RequestParam(defaultValue = "100") int size) {
        return userService.getUsers(PageRequest.of(page, size));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

    @GetMapping("/{userId}")
    public UserDao getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody CreateUserDao createUserDao) {
        Long userId = userService.createUser(createUserDao);

        return ResponseEntity.created(URI.create("api/v1/users/"+ userId)).build();
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserDao userDao) {
        userService.updateUser(userDao);
        return ResponseEntity.ok().build();
    }
}
