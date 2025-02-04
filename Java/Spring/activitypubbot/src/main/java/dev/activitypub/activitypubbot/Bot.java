package dev.activitypub.activitypubbot;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.Data;
import java.time.Instant;
import java.security.KeyPair;
import java.security.PublicKey;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import java.io.StringWriter;
import java.io.IOException;
import com.fasterxml.jackson.databind.annotation.JsonSerialize; 

/**
 * POJO for our Bot...
 */
@Data
@JsonSerialize(using = BotAPActorJsonSerializer.class)
public class Bot {

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    Long id; // does this even need to be exposed here?

    String username; // "preferredUsername: springbot",
    String name; // "Spring Bot",
    String summary; // "<p>A bot written using Java/Spring</p>",

    @Setter(AccessLevel.NONE)
    Instant published; // "2025-01-24T00:00:00Z",

    /**
     * The RSA key for the bot... TODO: thinking, with separatation of data maybe
     * the private key should not be "exposed" here and instead we provide only
     * services to do key operations... keeping priv key data "secret" to the model
     */
    @Setter(AccessLevel.NONE)
    KeyPair keyPair;

    /**
     * Get the string representation of the public key as required for the
     * ActivityPub key JSON.
     * "-----BEGIN PUBLIC KEY-----\\nMI [...] AB\\n-----END PUBLIC KEY-----"
     */
    public String getPublicKeyPEMString() {
        // Note: for performance it could be worth storing this in the db, or caching it in some way
        PublicKey pk = keyPair.getPublic();
        StringWriter w = new StringWriter();
        PEMWriter pw = new PEMWriter(w);
        try {
            pw.writeObject(new PemObject("PUBLIC KEY", pk.getEncoded()));
            pw.flush();
            pw.close();
            // TODO: flush and close with stringwriter?
            // Can an exception be thrown here using stringwriter?
        } catch (IOException e) {
            // FIXME: ?
        }
        return w.toString();
    }
    
    @Setter(AccessLevel.NONE)
    String type = "Person";

    @Setter(AccessLevel.NONE)
    boolean manuallyApproveFollowers = false;

    @Setter(AccessLevel.NONE)
    boolean indexable = false;

    @Override
    public String toString() {
        return "Bot: " + this.username + "\nKey: " + this.getPublicKeyPEMString() + "\n";
    }

    Bot() {
    }
    Bot( String username, String name, String summary, Instant published, KeyPair keyPair, String type, boolean manuallyApproveFollowers, boolean indexable ) {
        this.username = username;
        this.name = name;
        this.summary = summary;
        this.published = published;
        this.keyPair = keyPair;
        this.type = type;
        this.manuallyApproveFollowers = manuallyApproveFollowers;
        this.indexable = indexable;
    }
}

