package com.cat_backend.service;

import com.cat_backend.dto.CreateUserDTO;
import com.cat_backend.dto.UserDTO;

public interface UserService {
    UserDTO login(String username, String password);
    UserDTO register(CreateUserDTO dto);
}
