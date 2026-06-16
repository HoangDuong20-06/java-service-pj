package org.test.projectjavaservice.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.test.projectjavaservice.modal.dto.req.*;
import org.test.projectjavaservice.modal.dto.res.JwtResponse;
import org.test.projectjavaservice.modal.dto.res.UserResponse;
import org.test.projectjavaservice.service.*;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {
    private final AuthService authService = Mockito.mock(AuthService.class);
    private final SecurityAuthService securityAuthService = Mockito.mock(SecurityAuthService.class);
    private final TokenRefreshService tokenRefreshService = Mockito.mock(TokenRefreshService.class);
    private final LogoutService logoutService = Mockito.mock(LogoutService.class);

    private final MockMvc mockMvc =
            MockMvcBuilders.standaloneSetup(
                            new AuthController(
                                    authService,
                                    securityAuthService,
                                    tokenRefreshService,
                                    logoutService))
                    .build();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void registerTest() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("admin");
        request.setPassword("12345678");
        request.setEmail("admin@gmail.com");
        request.setFullName("Admin");
        request.setPhoneNumber("0123456789");

        Mockito.when(authService.register(any()))
                .thenReturn(new UserResponse());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void loginTest() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("12345678");

        Mockito.when(securityAuthService.authenticateUser(any()))
                .thenReturn(JwtResponse.builder().build());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void refreshTokenTest() throws Exception {
        TokenRefreshRequest request = new TokenRefreshRequest();
        request.setRefreshToken("abcxyz");
        Mockito.when(tokenRefreshService.refreshAccessToken(any()))
                .thenReturn(JwtResponse.builder().build());

        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void logoutTest() throws Exception {
        mockMvc.perform(post("/api/v1/auth/logout")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk());
    }

    @Test
    void forgotPasswordTest() throws Exception {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("test@gmail.com");
        mockMvc.perform(post("/api/v1/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}