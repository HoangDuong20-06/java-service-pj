package org.test.projectjavaservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.test.projectjavaservice.modal.TokenBlacklist;
import org.test.projectjavaservice.repository.TokenBlacklistRepository;
import org.test.projectjavaservice.security.jwt.JwtTokenProvider;
import org.test.projectjavaservice.service.LogoutService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    @Override
    public void logout(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token not valid or missing Header");
        }
        String token = authorizationHeader.substring(7);
        LocalDateTime expiryTime = jwtTokenProvider.getExpirationDateFromToken(token);
        TokenBlacklist blacklistedToken = new TokenBlacklist();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpiryTime(expiryTime);
        tokenBlacklistRepository.save(blacklistedToken);
    }
}
