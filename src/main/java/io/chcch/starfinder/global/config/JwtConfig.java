package io.chcch.starfinder.global.config;

import java.util.Map;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "custom.jwt")
public class JwtConfig {

    private Map<String, String> secrets;
    private Map<String, Long> validation;


}
