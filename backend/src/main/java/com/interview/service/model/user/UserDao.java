package com.interview.service.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDao {
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
}
