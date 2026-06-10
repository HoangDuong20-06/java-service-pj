package org.test.projectjavaservice.service;

import org.springframework.stereotype.Service;
import org.test.projectjavaservice.modal.dto.req.TokenRefreshRequest;
import org.test.projectjavaservice.modal.dto.res.JwtResponse;

@Service
public interface TokenRefreshService {
    JwtResponse refreshAccessToken(TokenRefreshRequest request);
}
