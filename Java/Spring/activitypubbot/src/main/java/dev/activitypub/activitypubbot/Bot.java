package dev.activitypub.activitypubbot;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.Data;
import java.time.Instant;

/**
 * POJO for our Bot...
 */
@Data
public class Bot {

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    Long id; // does this even need to be exposed here?

    String username; // "preferredUsername: springbot",
    String name; // "Spring Bot",
    String summary; // "<p>A bot written using Java/Spring</p>",

    @Setter(AccessLevel.NONE)
    Instant published; // "2025-01-24T00:00:00Z",

    @Setter(AccessLevel.NONE)
    String publicKeyPem; // "-----BEGIN PUBLIC KEY-----\\nMI [...] AB\\n-----END PUBLIC KEY-----"
    
    @Setter(AccessLevel.NONE)
    String type = "Person";

    @Setter(AccessLevel.NONE)
    boolean manuallyApproveFollowers = false;

    @Setter(AccessLevel.NONE)
    boolean indexable = false;

    @Override
    public String toString() {
        return "Bot: " + this.username;
    }

    Bot() {
    }
    Bot( String username, String name, String summary, String type, boolean manuallyApproveFollowers, boolean indexable, String pubicKeyPem ) {
        this.username = username;
        this.name = name;
        this.summary = summary;
        this.type = type;
        this.manuallyApproveFollowers = manuallyApproveFollowers;
        this.indexable = indexable;
        this.publicKeyPem = publicKeyPem;
    }
}

