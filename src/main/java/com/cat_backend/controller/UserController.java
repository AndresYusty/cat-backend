package com.cat_backend.controller;

import com.cat_backend.dto.CreateUserDTO;
import com.cat_backend.dto.UserDTO;
import com.cat_backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService svc;
    public UserController(UserService svc) { this.svc = svc; }

    @GetMapping("/login")
    public UserDTO login(@RequestParam String username,
                         @RequestParam String password) {
        return svc.login(username, password);
    }

    @GetMapping("/register")
    public UserDTO register(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String fullName) {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setFullName(fullName);
        return svc.register(dto);
    }
}
