package dev.activitypub.activitypubbot;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface BotRepo extends JpaRepository<Bot, Long> {
 
}
