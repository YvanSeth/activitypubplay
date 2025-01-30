package dev.activitypub.activitypubbot;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    // whilst the URI structure of the below are up to the implementor we're using Mastodon as a reference

    // TODO: perhaps these could just be templated elsewhere... or part of a separat JSON generator

    /**
     * Generate the users URI
     */
    private String getUsersURI() {
        return props.getScheme() + "://" + props.getDomain() + "/users/";
    }

    /**
     * The"id" of a bot (user/actor) is a URI combining domain and username: https://<domain>/users/<username>
     */
    public String getId(Bot bot) {
        return getUsersURI() + bot.getUsername();
    }

    /**
     * The "inbox" of a user is a URI of the form: https://<domain>/users/<username>/inbox
     */
    public String getInbox(Bot bot) {
        return getUsersURI() + bot.getUsername() + "/inbox";
    }

    /**
     * The "outbox" of a user is a URI of the form: https://<domain>/users/<username>/outbox
     */
    public String getOutbox(Bot bot) {
        return getUsersURI() + bot.getUsername() + "/outbox";
    }

    public Bot save(Bot bot) {
        return repo.save(bot);
    }
}

