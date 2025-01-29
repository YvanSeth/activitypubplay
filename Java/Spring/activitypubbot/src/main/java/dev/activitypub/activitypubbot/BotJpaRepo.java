package dev.activitypub.activitypubbot;

import org.springframework.stereotype.Repository;

import java.util.List; 
import java.util.Optional; 
import java.util.stream.Collectors; 

/**
 * CRUD layer? TODO: Does "JPA" make sense in the name here?
 */
@Repository
public class BotJpaRepo implements BotRepo {

    // underlying BotModel storage
    private final BotJdbcRepo botJdbcRepo;

    public BotJpaRepo(BotJdbcRepo botJdbcRepo) {
        this.botJdbcRepo = botJdbcRepo;
    }

    @Override
    public Bot save(Bot bot) {
        BotModel botModel = BotModel.from(bot);
        BotModel saved = botJdbcRepo.save(botModel);
        return saved.asBot();
        // this pattern comes from the 'ensembler' MemberRepositoryJdbcAdapter impl
        // TODO: in our case I'm not sure we need to be returning a Bot, could be void?
    }

    public Optional<Bot> findByUsername(String username) {
        // username is a unique key so technically should only be one of these...?
        Optional<BotModel> botModels = botJdbcRepo.findByUsername(username);
        return botModels.map(BotModel::asBot);
    }

    public List<Bot> findAll() {
        List<BotModel> botModels = botJdbcRepo.findAll();
        return botModels.stream().map(BotModel::asBot).collect(Collectors.toList());
    }
}
