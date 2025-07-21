package com.cat_backend.service;

import com.cat_backend.dto.CreateUserDTO;
import com.cat_backend.dto.UserDTO;
import com.cat_backend.entity.User;
import com.cat_backend.exception.InvalidCredentialsException;
import com.cat_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository repo;

    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    UserServiceImpl service;

    @Test
    void register_deberíaGuardarYDevolverDTO() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setUsername("u1");
        dto.setPassword("pass");
        dto.setFullName("Usuario Uno");

        when(encoder.encode("pass")).thenReturn("HASHED");
        User saved = new User();
        saved.setId(10L);
        saved.setUsername("u1");
        saved.setPassword("HASHED");
        saved.setFullName("Usuario Uno");
        when(repo.save(any(User.class))).thenReturn(saved);

        UserDTO result = service.register(dto);
        assertEquals(10L, result.getId());
        assertEquals("u1", result.getUsername());
        assertEquals("Usuario Uno", result.getFullName());

        // verify que se codificó la contraseña
        verify(encoder).encode("pass");
    }

    @Test
    void login_conCredencialesVálidas_deberíaDevolverDTO() {
        User u = new User();
        u.setId(5L);
        u.setUsername("u2");
        u.setPassword("HASH");
        u.setFullName("User Dos");

        when(repo.findByUsername("u2")).thenReturn(Optional.of(u));
        when(encoder.matches("plain", "HASH")).thenReturn(true);

        UserDTO dto = service.login("u2", "plain");
        assertEquals("u2", dto.getUsername());
        assertEquals(5L, dto.getId());
    }

    @Test
    void login_usuarioNoExiste_debeLanzar() {
        when(repo.findByUsername("nope")).thenReturn(Optional.empty());
        assertThrows(InvalidCredentialsException.class,
                () -> service.login("nope", "x"));
    }

    @Test
    void login_contraseñaInválida_debeLanzar() {
        User u = new User();
        u.setUsername("u3");
        u.setPassword("H");
        when(repo.findByUsername("u3")).thenReturn(Optional.of(u));
        when(encoder.matches("bad", "H")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class,
                () -> service.login("u3", "bad"));
    }
}
