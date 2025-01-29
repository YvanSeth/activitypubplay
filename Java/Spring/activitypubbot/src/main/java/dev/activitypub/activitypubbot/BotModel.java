package dev.activitypub.activitypubbot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Our core Bot (aka user) data as stored persistently in the database.
 * This is all the key non-derived data as required by the ActivityPub
 * specification. NOTE: This is not a comprehensive implementation!
 */
@Entity
@Table(name = "bot")
@EntityListeners(AuditingEntityListener.class)
public class BotModel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;


    ///////////////////////////////////////////////////////////////////////////
    // The next values are user-supplied

    /**
     * Username of the bot, i.e. the value after the @: @&lt;username&gt;@&lt;domain&gt;
     * Note that in the Activity Pub spec this is encoded as 'preferredUsername',
     * we shorten just to username here for clarity and brevity in the code.
     *
     * @param username the username value
     * @return the username value
     */
    @Column(nullable=false,unique=true)
    @Getter @Setter private String username; // "preferredUsername: springbot",

    /**
     * The "friendly" formatted name of the bot, can have spaces, etc.
     * 
     * @param name a presentational name for the bot
     * @return the presentational name for the bot
     */
    @Column(nullable=true,unique=false)
    @Getter @Setter private String name; // "Spring Bot",

    /**
     * A bit of text to describe the bot/account, an "about".
     * 
     * @param summary Text describing the bot/account
     * @return the summary string
     */
    @Column(nullable=true,unique=false)
    @Getter @Setter private String summary; // "<p>A bot written using Java/Spring</p>",

    ///////////////////////////////////////////////////////////////////////////
    // The following values will be auto-generated on bot-creation

    @CreatedDate // TODO: is this really the right approach to auto-timestamp, not sure about all this "auditing" stuff, feels like a misuse
    @Getter private Instant published; // "2025-01-24T00:00:00Z",

    // TODO how and where do we generate this beastie?!?!
    @Column(nullable=true,unique=false) // FIXME: this isn't true, just easy for now
    @Getter private String publicKeyPem; // "-----BEGIN PUBLIC KEY-----\\nMI [...] AB\\n-----END PUBLIC KEY-----"
    
    @Column(nullable=false,unique=false)
    @Getter private String type;

    @Column(nullable=false,unique=false)
    @Getter private boolean manuallyApproveFollowers;

    @Column(nullable=false,unique=false)
    @Getter private boolean indexable;

    static BotModel from(Bot bot) {
        BotModel bm = new BotModel();
        bm.username = bot.getUsername();
        bm.name = bot.getName();
        bm.summary = bot.getSummary();
        bm.type = bot.getType();
        bm.manuallyApproveFollowers = bot.isManuallyApproveFollowers();
        bm.indexable = bot.isIndexable();
        bm.publicKeyPem = bot.getPublicKeyPem();
        return bm;
    }

    Bot asBot() {
        return new Bot( username, name, summary, published, publicKeyPem, type, manuallyApproveFollowers, indexable );
    }

    @Override
    public String toString() {
        return "Bot: " + this.username;
    }

}
