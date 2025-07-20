package com.cat_backend.dto;

import lombok.Data;

@Data
public class CreateUserDTO {
    private String username;
    private String password;
    private String fullName;
}