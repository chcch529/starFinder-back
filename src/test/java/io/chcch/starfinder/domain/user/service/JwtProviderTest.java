package io.chcch.starfinder.domain.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.chcch.starfinder.domain.user.dto.TokenBody;
import io.chcch.starfinder.domain.user.entity.Role;
import io.chcch.starfinder.global.config.TestSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Slf4j
@SpringBootTest
@Import(TestSecurityConfig.class)
class JwtProviderTest {

    @Autowired
    JwtProvider provider;

    @Test
    @DisplayName("jwt 발급 테스트")
    void jwt_issue_test() throws Exception {

        Long targetId = 1L;
        Role targetRole = Role.MEMBER;

        String accessToken = provider.issueAccessToken(targetId, targetRole);
        log.info("accessToken = {}", accessToken);

    }

    @Test
    @DisplayName("토큰 유효성 검사 테스트")
    void token_validate_test() throws Exception {

        String targetToken1 = "eyJhbGciJIINifJ9.eyJzdWIiOxwi9sZSI61FTUUiIslhdCTc0ND0NjNiiZwIjoNzQ0OQ2Ok2fQ.YUBvLn-sLe8tLCrFZJlGPO7S9YM_PRXQ";

        String targetToken2 = "gooodmoring";

        boolean validate1 = provider.validate(targetToken1);
        boolean validate2 = provider.validate(targetToken2);

        log.info("validate1 = {}", validate1);
        log.info("validate2 = {}", validate2);

    }

    @Test
    @DisplayName("jwt parse test")
    void parse_jwt_test() throws Exception {

        // given
        Long targetId = 100L;
        Role targetRole = Role.MEMBER;

        String accessToken = provider.issueAccessToken(targetId, targetRole);

        boolean result = provider.validate(accessToken);
        assertThat(result).isTrue();

        // when
        TokenBody tokenBody = provider.parseJwt(accessToken);

        // then
        assertThat(tokenBody.getUserId()).isEqualTo(targetId);
        assertThat(tokenBody.getRole()).isEqualTo(targetRole);
    }
}