package com.interview.service;


import com.interview.database.model.DbUser;
import com.interview.database.repository.UserRepository;
import com.interview.exception.IllegalArgumentException;
import com.interview.exception.NotFoundException;
import com.interview.mapper.PageResponseMapper;
import com.interview.mapper.UserMapper;
import com.interview.service.model.user.CreateUserDao;
import com.interview.service.model.PagedResponse;
import com.interview.service.model.user.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
        DbUser dbUser = UserMapper.createUserDaoToDbUserFunction().apply(createUserDao);
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
}
