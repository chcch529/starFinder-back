package io.chcch.starfinder.domain.user.service;

import io.chcch.starfinder.domain.user.dto.OAuthUserDetails;
import io.chcch.starfinder.domain.user.entity.Role;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        OAuthUserDetails oAuthUserDetails = OAuthUserDetailsFactory.oAuthUserDetails(provider,
            oAuth2User);

        User getUser = userRepository.findByEmail(oAuthUserDetails.getEmail())
            .orElseGet(
                () -> {
                    User saved = User.builder()
                        .name(oAuthUserDetails.getName())
                        .nickname(oAuthUserDetails.getName())
                        .email(oAuthUserDetails.getEmail())
                        .role(Role.valueOf(oAuthUserDetails.getRole()))
                        .build();

                    return userRepository.save(saved);
                }
            );

        if (getUser.getProvider().equals(provider)) {
            return oAuthUserDetails;
        } else {
            throw new RuntimeException();
        }
    }
}
