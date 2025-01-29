package dev.activitypub.activitypubbot;
 
import java.util.Optional;
import java.util.List;

/**
 * Interface for a Bot respository.
 *
 * Working to the @Repository documentation and some other examples
 * for this, a pattern noted to be "close to the DAO pattern". But I
 * am not yet clear on the need/justification for this approach.
 */
public interface BotRepo {

    Bot save(Bot bot);

    Optional<Bot> findByUsername(String username);

    List<Bot> findAll();
}
