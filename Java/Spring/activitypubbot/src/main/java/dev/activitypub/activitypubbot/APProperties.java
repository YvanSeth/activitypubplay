package dev.activitypub.activitypubbot;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Object bound to "ap" prefix in {@link org.springframework.core.env.Environment}.
 *
 * @author Yvan Seth
 */
@Configuration
@ConfigurationProperties(prefix = "ap")
public class APProperties {

    /**
     * Key File Path
     */
    private String keyFilePath = "keyfile.pem";

    /**
     * Server domain
     */
    private String serverDomain = "activitypub.bot";

    public String getKeyFilePath() {
        return keyFilePath;
    }
    public void setKeyFilePath(String keyFilePath) {
        this.keyFilePath = keyFilePath;
    }
    public String getServerDomain() {
        return serverDomain;
    }
    public void setServerDomain(String serverDomain) {
        this.serverDomain = serverDomain;
    }
}
