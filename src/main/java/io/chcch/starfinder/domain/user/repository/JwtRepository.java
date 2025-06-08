package io.chcch.starfinder.domain.user.repository;

import io.chcch.starfinder.domain.user.entity.RefreshToken;
import io.chcch.starfinder.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken save(User user, String token);
    Optional<RefreshToken> findValidRefToken(Long userId);
}
