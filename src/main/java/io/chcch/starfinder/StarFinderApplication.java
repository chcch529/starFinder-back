package io.chcch.starfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class StarFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarFinderApplication.class, args);
    }

}
