package com.hdfc.library.User.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String accountStatus;
}
