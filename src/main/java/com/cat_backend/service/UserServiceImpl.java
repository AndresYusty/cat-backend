package com.cat_backend.service;

import com.cat_backend.dto.CreateUserDTO;
import com.cat_backend.dto.UserDTO;
import com.cat_backend.entity.User;
import com.cat_backend.exception.InvalidCredentialsException;
import com.cat_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UserDTO login(String username, String password) {
        User u = repo.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Usuario o contraseña inválidos"));
        if (!encoder.matches(password, u.getPassword())) {
            throw new InvalidCredentialsException("Usuario o contraseña inválidos");
        }
        return map(u);
    }

    @Override
    public UserDTO register(CreateUserDTO dto) {
        String hashed = encoder.encode(dto.getPassword());
        User u = User.builder()
                .username(dto.getUsername())
                .password(hashed)
                .fullName(dto.getFullName())
                .build();
        User saved = repo.save(u);
        return map(saved);
    }

    private UserDTO map(User u) {
        UserDTO d = new UserDTO();
        d.setId(u.getId());
        d.setUsername(u.getUsername());
        d.setFullName(u.getFullName());
        return d;
    }
}
