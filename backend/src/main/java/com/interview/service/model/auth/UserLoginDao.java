package com.interview.service.model.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserLoginDao {

    @NotNull
    private String userName;
    @NotNull
    private String password;
}
