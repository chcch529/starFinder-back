package io.chcch.starfinder.domain.user.service;

import io.chcch.starfinder.domain.user.dto.OAuthUserDetails;
import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuthUserDetailsFactory {

    public static OAuthUserDetails oAuthUserDetails(String provider, OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        switch (provider.toUpperCase()) {
            case "GOOGLE" -> {
                return OAuthUserDetails.builder()
                    .name(attributes.get("name").toString())
                    .email(attributes.get("email").toString())
                    .attributes(attributes)
                    .build();
            }
            case "KAKAO" -> {
                Map<String, String> properties = (Map<String, String>) attributes.get("properties");

                return OAuthUserDetails.builder()
                    .name(properties.get("nickname"))
                    .email(attributes.get("id").toString() + "@kakao.com")
                    .attributes(attributes)
                    .build();
            }

            default -> throw new IllegalArgumentException("지원하지 않는 제공자 " + provider);

        }
    }

}
