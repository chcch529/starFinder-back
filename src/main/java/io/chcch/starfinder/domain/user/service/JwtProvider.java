package io.chcch.starfinder.domain.user.service;

import io.chcch.starfinder.domain.user.dto.TokenBody;
import io.chcch.starfinder.domain.user.dto.TokenPair;
import io.chcch.starfinder.domain.user.entity.RefreshToken;
import io.chcch.starfinder.domain.user.entity.Role;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.repository.JwtRepository;
import io.chcch.starfinder.global.config.JwtConfig;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtConfig jwtConfig;
    private final JwtRepository jwtRepository;

    public TokenPair generateTokenPair(User user) {
        Long userId = user.getId();
        Role role = user.getRole();

        String accessToken = issueAccessToken(userId, role);
        String refreshToken = issueRefreshToken(userId, role);

        jwtRepository.save(user, refreshToken);

        return TokenPair.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public Optional<RefreshToken> findRefreshToken(Long userId) {
        return jwtRepository.findValidRefToken(userId);
    }

    public String issueAccessToken(Long id, Role role) {
        return issue(id, role, jwtConfig.getValidation().get("access"));
    }
    public String issueRefreshToken(Long id, Role role) {
        return issue(id, role, jwtConfig.getValidation().get("refresh"));
    }

    private String issue(Long id, Role role, Long expTime) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expTime);

        return Jwts.builder()
            .subject(id.toString())
            .claim("role", role)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(getSecretKey(), SIG.HS256)
            .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token);

            return true;
        } catch (JwtException e) {
            log.warn("Invalid JWT token: {}", token);
        }
        return false;
    }

    public TokenBody parseJwt(String token) {
        Jws<Claims> parsed = Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token);

        String subject = parsed.getPayload().getSubject();
        String role = parsed.getPayload().get("role").toString();

        return new TokenBody(
            Long.parseLong(subject), Role.valueOf(role)
        );
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecrets().get("app-key").getBytes(StandardCharsets.UTF_8));
    }

}
