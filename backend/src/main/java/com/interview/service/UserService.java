package com.interview.service;


import com.interview.database.model.DbUser;
import com.interview.database.repository.UserRepository;
import com.interview.exception.ConflictException;
import com.interview.exception.IllegalArgumentException;
import com.interview.exception.NotFoundException;
import com.interview.mapper.PageResponseMapper;
import com.interview.mapper.UserMapper;
import com.interview.service.model.user.CreateUserDao;
import com.interview.service.model.PagedResponse;
import com.interview.service.model.user.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Returns a user by id
     *
     * @param userId userId to search for
     * @return the found user or throws exception
     */
    public UserDao getUser(@Valid @NotNull Long userId) {
        return userRepository.findById(userId)
                .map(UserMapper.dbUserToUserDaoFunction())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public PagedResponse<UserDao> getUsers(Pageable pageable) {
        Page<DbUser> dbUsersPageResponse = userRepository.findAll(pageable);

        return PageResponseMapper.pageToPageResponseMap(
                dbUsersPageResponse,
                UserMapper.dbUserToUserDaoFunction());
    }

    public Long createUser(@Valid @NotNull CreateUserDao createUserDao) {
        if (createUserDao.getUserId() != null) {
            throw new IllegalArgumentException("Id should not be provided for a new user");
        }

        if (userRepository.findDbUserByUserName(createUserDao.getUserName()).isPresent()) {
            throw new ConflictException("User name already exists");
        }

        DbUser dbUser = UserMapper.createUserDaoToDbUserFunction().apply(createUserDao);

        // encrypt password
        dbUser.setPassword(passwordEncoder.encode(createUserDao.getPassword()));

        return userRepository.save(dbUser).getUserId();
    }

    public void updateUser(@Valid @NotNull UserDao userDao) {
        Optional<DbUser> dbUserOptional = userRepository.findById(userDao.getUserId());

        if (!dbUserOptional.isPresent()) {
            throw new NotFoundException("User not found");
        }

        DbUser updatedDbUser = UserMapper.userDaoToDbUserSkipPasswordFunction().apply(userDao);

        // handle password
        updatedDbUser.setPassword(dbUserOptional.get().getPassword());

        userRepository.save(updatedDbUser);
    }

    public void deleteUserById(@Valid @NotNull @Min(0) Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            // ignored in case user already deleted
        }
    }
}
