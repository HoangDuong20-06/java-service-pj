package org.test.projectjavaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.projectjavaservice.modal.RefreshToken;
import org.test.projectjavaservice.modal.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(User user);
    Optional<RefreshToken> findByToken(String token);
}
