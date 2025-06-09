package io.chcch.starfinder.global.config;

import io.chcch.starfinder.domain.user.dto.OAuthUserDetails;
import io.chcch.starfinder.domain.user.entity.RefreshToken;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.service.CustomOAuth2UserService;
import io.chcch.starfinder.domain.user.service.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final CustomOAuth2UserService oAuth2UserService;
    private final JwtProvider jwtProvider;

    private static String genJsonToken(String accessToken, String refreshToken) {
        return String.format("""
            {
                "accesssToken": "%s",
                "refreshToken": "%s"
            }
            """, accessToken, refreshToken);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        OAuthUserDetails principal = (OAuthUserDetails) authentication.getPrincipal();
        Long userId = principal.getId();

        String accessToken = jwtProvider.issueAccessToken(userId, principal.getRole());
        User user = oAuth2UserService.getById(userId);

        String refreshToken = jwtProvider.findRefreshToken(userId)
            .map(RefreshToken::getRefreshToken)
            .orElseGet(() -> jwtProvider.generateTokenPair(user).getRefreshToken());

        String json = genJsonToken(accessToken, refreshToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
