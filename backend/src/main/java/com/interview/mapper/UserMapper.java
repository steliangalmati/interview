package com.interview.mapper;

import com.interview.database.model.DbUser;
import com.interview.service.model.user.CreateUserDao;
import com.interview.service.model.user.UserDao;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static Function<DbUser, UserDao> dbUserToUserDaoFunction() {
        return (dbUser) -> {
            if (dbUser == null) {
                return null;
            }
            return UserDao.builder()
                    .userId(dbUser.getUserId())
                    .userName(dbUser.getUserName())
                    .firstName(dbUser.getFirstName())
                    .lastName(dbUser.getLastName())
                    .build();
        };
    }

    public static Function<UserDao, DbUser> userDaoToDbUserSkipPasswordFunction() {
        return (userDao) -> {
            if (userDao == null) {
                return null;
            }
            return DbUser.builder()
                    .userId(userDao.getUserId())
                    .userName(userDao.getUserName())
                    .firstName(userDao.getFirstName())
                    .lastName(userDao.getLastName())
                    // handle password externally
                    .build();
        };
    }

    public static Function<CreateUserDao, DbUser> createUserDaoToDbUserFunction() {
        return (createUserDao) -> {
            if (createUserDao == null) {
                return null;
            }
            return DbUser.builder()
                    .userId(createUserDao.getUserId())
                    .userName(createUserDao.getUserName())
                    .firstName(createUserDao.getFirstName())
                    .lastName(createUserDao.getLastName())
                    .password(createUserDao.getPassword())
                    .build();
        };
    }
}