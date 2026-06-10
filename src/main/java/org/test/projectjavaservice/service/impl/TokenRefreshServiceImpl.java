package org.test.projectjavaservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.test.projectjavaservice.modal.RefreshToken;
import org.test.projectjavaservice.modal.User;
import org.test.projectjavaservice.modal.dto.req.TokenRefreshRequest;
import org.test.projectjavaservice.modal.dto.res.JwtResponse;
import org.test.projectjavaservice.repository.RefreshTokenRepository;
import org.test.projectjavaservice.security.jwt.JwtTokenProvider;
import org.test.projectjavaservice.service.TokenRefreshService;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenRefreshServiceImpl implements TokenRefreshService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public JwtResponse refreshAccessToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        RefreshToken token = refreshTokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Refresh token not exist"));
        if (token.getExpiryDate().isBefore(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Refresh token out of date . Please Login again");
        }
        User user = token.getUser();
        String newAccessToken = jwtTokenProvider.generateAccessToken(user);

        return JwtResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(token.getToken())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
