package org.test.projectjavaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.projectjavaservice.modal.TokenBlacklist;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    TokenBlacklist save(TokenBlacklist tokenBlacklist);
    boolean existsByToken(String token);
}
