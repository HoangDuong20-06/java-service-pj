package org.test.projectjavaservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.test.projectjavaservice.modal.RefreshToken;
import org.test.projectjavaservice.modal.User;
import org.test.projectjavaservice.modal.dto.req.LoginRequest;
import org.test.projectjavaservice.modal.dto.res.JwtResponse;
import org.test.projectjavaservice.repository.RefreshTokenRepository;
import org.test.projectjavaservice.repository.UserRepository;
import org.test.projectjavaservice.security.jwt.JwtTokenProvider;
import org.test.projectjavaservice.service.SecurityAuthService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityAuthServiceImpl implements SecurityAuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public JwtResponse authenticateUser(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect");
        }
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshTokenStr = UUID.randomUUID().toString();
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setToken(refreshTokenStr);
        refreshToken.setExpiryDate(Instant.now().plusMillis(7 * 24 * 60 * 60 * 1000));
        refreshTokenRepository.save(refreshToken);
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenStr)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
