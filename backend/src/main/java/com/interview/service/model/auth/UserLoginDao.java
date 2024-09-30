package com.interview.service.model.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserLoginDao {

    @NotNull
    private String userName;
    @NotNull
    private String password;
}
