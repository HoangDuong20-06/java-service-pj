package org.test.projectjavaservice.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.test.projectjavaservice.modal.User;
import org.test.projectjavaservice.modal.dto.req.ChangePasswordRequest;
import org.test.projectjavaservice.modal.dto.req.ForgotPasswordRequest;
import org.test.projectjavaservice.modal.dto.req.RegisterRequest;
import org.test.projectjavaservice.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthServiceImpl authService;

    @Test
    void registerSuccess() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("admin");
        request.setEmail("admin@gmail.com");
        request.setPassword("123456");

        when(userRepository.existsByUsername("admin")).thenReturn(false);
        when(userRepository.existsByEmail("admin@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        User user = new User();
        user.setId(1L);
        user.setUsername("admin");

        when(userRepository.save(any(User.class))).thenReturn(user);

        assertNotNull(authService.register(request));
    }

    @Test
    void registerFailUsernameExist() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("admin");

        when(userRepository.existsByUsername("admin")).thenReturn(true);

        assertThrows(ResponseStatusException.class,
                () -> authService.register(request));
    }

    @Test
    void changePasswordSuccess() {

        User user = new User();
        user.setPassword("old");

        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword("123");
        request.setNewPassword("456");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123", "old"))
                .thenReturn(true);

        when(passwordEncoder.encode("456"))
                .thenReturn("encoded");

        authService.changePassword(request, "admin");

        verify(userRepository).save(user);
    }

    @Test
    void changePasswordWrongOldPassword() {

        User user = new User();
        user.setPassword("old");

        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword("123");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> authService.changePassword(request, "admin"));
    }

    @Test
    void resetPasswordSuccess() {

        User user = new User();
        user.setEmail("test@gmail.com");

        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.encode(anyString()))
                .thenReturn("encoded");

        authService.resetPassword(request);

        verify(userRepository).save(user);
    }
}