package dev.activitypub.activitypubbot;
 
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Database shenanigans...
 */
public interface BotRepo extends JpaRepository<Bot, Long> {
 
}
