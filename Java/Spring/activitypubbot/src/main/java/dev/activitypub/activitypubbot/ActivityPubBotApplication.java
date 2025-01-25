package dev.activitypub.activitypubbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableJpaAuditing
public class ActivityPubBotApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ActivityPubBotApplication.class);
    }
}
