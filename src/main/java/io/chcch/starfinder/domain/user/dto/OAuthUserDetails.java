package io.chcch.starfinder.domain.user.dto;

import io.chcch.starfinder.domain.user.entity.Role;
import io.chcch.starfinder.domain.user.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Accessors(chain = true)
@NoArgsConstructor
public class OAuthUserDetails implements OAuth2User {

    @Setter
    private Long id;

    private String name;
    private String email;
    private Map<String, Object> attributes;

    @Setter
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public static OAuthUserDetails from(User user) {
        OAuthUserDetails oAuthUserDetails = new OAuthUserDetails();

        oAuthUserDetails.name = user.getName();
        oAuthUserDetails.email = user.getEmail();
        oAuthUserDetails.role = user.getRole();

        return oAuthUserDetails;
    }

    @Builder
    public OAuthUserDetails(String name, String email, Map<String, Object> attributes) {
        this.name = name;
        this.email = email;
        this.attributes = attributes;
    }
}
