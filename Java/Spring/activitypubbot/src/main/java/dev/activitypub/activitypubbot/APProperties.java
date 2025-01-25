package dev.activitypub.activitypubbot;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Object bound to "springbot" prefix in {@link org.springframework.core.env.Environment}.
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

    /*String getScheme() { return this.scheme; }
    String getDomain() { return this.domain; }
    void setScheme( String scheme ) { this.scheme = scheme; }
    void setDomain( String domain ) { this.domain = domain; }*/
}
