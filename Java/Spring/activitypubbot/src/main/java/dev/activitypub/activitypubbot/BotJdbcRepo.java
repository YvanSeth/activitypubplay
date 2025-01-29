package dev.activitypub.activitypubbot;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional; 
import java.util.List; 

public interface BotJdbcRepo extends CrudRepository<BotModel, Long> {
    Optional<BotModel> findByUsername(String username);
    List<BotModel> findAll();
}

