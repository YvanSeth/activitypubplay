package dev.activitypub.activitypubbot;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

/**
 * The "business logic" and derived data of a Bot.
 */
@Service
public class BotService {

    @Autowired
    private BotRepo repo;

    @Autowired
    private APProperties props;

    @Transactional
    public Bot create(Bot bot) {
        // A user must have a key, so we generate and associate one on creation
        // https://docs.joinmastodon.org/spec/activitypub/#publicKey
        return repo.save(bot);
    }

    // so far these methods just being calls to repo make me wonder why we need a service in the first place...
    public List<Bot> findAll() {
        return repo.findAll();
    }

    public Bot getBotByUsername( String username ) {
        return repo.findByUsername( username ).orElseThrow(() -> new BotNotFoundByUsernameException(username));
    }


    // whilst the URI structure of the below are up to the implementer we're using Mastodon as a reference

    // TODO: I've fiddled around loads with where to house/generate the values
    // from the calls below, they have ended up here mainly as this is where it
    // is cleanest to access the configuration properties.
    //
    // Opinion in material I've read seems very firm that knowledge of
    // "configuration" should not exist at Entity level so I have not made them
    // members of the Bot class. I've toyed with the idea of them simply all
    // being kept as database values and generated on creation... and in some
    // ways that is simply cleaner and thus still has an appeal to it. I just
    // also feel uncomfortable about having a load of "derived data" in the
    // database, even though there is also a efficiency argument. Perhaps the
    // combination of both efficiency + design-readability should win it. (In
    // which case this code could well just stay here to be used at creation.)

    /**
     * Generate the webfinger subject
     */
    public String getWebfingerSubject(Bot bot) {
        return "acct:" + bot.getUsername() + "@" + props.getDomain();
    }

    /**
     * Generate the webfinger subject
     */
    public String getHandle(Bot bot) {
        return "@" + bot.getUsername() + "@" + props.getDomain();
    }

    /**
     * Generate the base users URI
     */
    private UriComponentsBuilder getUsersURI() {
        return UriComponentsBuilder.newInstance()
            .scheme(props.getScheme())
            .host(props.getDomain())
            .pathSegment("users");
    }

    /**
     * The "id" of a bot (user/actor) is a URI combining domain and username: https://<domain>/users/<username>
     */
    public String getId(Bot bot) {
        return getUsersURI()
                .pathSegment(bot.getUsername())
                .toUriString();
    }

    /**
     * The "inbox" of a user is a URI of the form: https://<domain>/users/<username>/inbox
     */
    public String getInbox(Bot bot) {
        return getUsersURI()
                .pathSegment(bot.getUsername())
                .pathSegment("inbox")
                .toUriString();
    }

    /**
     * The "outbox" of a user is a URI of the form: https://<domain>/users/<username>/outbox
     */
    public String getOutbox(Bot bot) {
        return getUsersURI()
                .pathSegment(bot.getUsername())
                .pathSegment("outbox")
                .toUriString();
    }

    public Bot save(Bot bot) {
        return repo.save(bot);
    }
}

