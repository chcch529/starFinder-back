package io.chcch.starfinder.domain.user.service;

import io.chcch.starfinder.domain.user.dto.LoginRequest;
import io.chcch.starfinder.domain.user.dto.LoginResponse;
import io.chcch.starfinder.domain.user.dto.OAuthUserDetails;
import io.chcch.starfinder.domain.user.dto.UserSignUpDto;
import io.chcch.starfinder.domain.user.entity.RefreshToken;
import io.chcch.starfinder.domain.user.entity.Role;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.mapper.UserMapper;
import io.chcch.starfinder.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void save(UserSignUpDto userSignUpDto) {
        User user = UserMapper.toEntity(userSignUpDto);
        userRepository.save(user);

    }

    @Transactional(readOnly = true)
    public boolean availableEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public LoginResponse authenticateAndGenerateToken(LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // id와 role 추출 위해 OAuthUserDetails 활용
            OAuthUserDetails principal = (OAuthUserDetails) authenticate.getPrincipal();

            Long userId = principal.getId();
            Role userRole = principal.getRole();

            String accessToken = jwtProvider.issueAccessToken(userId, userRole);

            String refreshToken = jwtProvider.findRefreshToken(userId)
                .map(RefreshToken::getRefreshToken)
                .orElseGet(() -> {
                    User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

                    return jwtProvider.generateTokenPair(user).getRefreshToken();
                });

            return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        } catch (AuthenticationException e) {
            throw e;
        }
    }

}
