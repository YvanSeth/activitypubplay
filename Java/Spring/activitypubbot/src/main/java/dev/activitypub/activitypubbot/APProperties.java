package dev.activitypub.activitypubbot;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Using the "springbot" prefix for our configuration values.
 */
@Configuration
@ConfigurationProperties(prefix = "springbot")
public class APProperties {

    /**
     * Scheme (i.e. https)
     */
    @Getter @Setter private String scheme; // = "https";

    /**
     * Server domain - i.e. https://&lt;domain&gt;/@username
     */
    @Getter @Setter private String domain; // = "activitypub.bot";
}
