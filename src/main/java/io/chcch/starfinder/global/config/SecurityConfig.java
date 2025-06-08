package io.chcch.starfinder.global.config;

import io.chcch.starfinder.domain.user.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("!test")
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/login/oauth2/**", "/auth/**").permitAll()
//                .anyRequest().authenticated())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(oAuth2UserService))
                .successHandler(oAuth2SuccessHandler))
            .addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class)

            .build();
    }


}
