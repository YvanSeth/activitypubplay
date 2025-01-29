package dev.activitypub.activitypubbot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * This handles web requests relating to our bots (users) - so things like
 * /@username and /users/username.
 */
@Controller
@Slf4j
public class BotController {

    @Autowired
    private BotService botServ;

    @GetMapping("/viewbot")
    public String listAll(Model model) {
        log.info("BotController::viewbot");

        List<Bot> botlist = botServ.findAll();
        model.addAttribute("bots", botlist);

        return "listbots";
    }

    @GetMapping("/viewbot/{username}")
    public String listAll(@PathVariable String username, Model model) {
        log.info("BotController::viewbot/" + username);

        Bot bot = botServ.getBotByUsername(username);
        model.addAttribute("bot", bot);

        return "viewbot";
    }

    @GetMapping("/makebot")
    public String makebotget(Model model) {
        log.info("BotController::makebot");

        Bot bot = new Bot();
        model.addAttribute("bot", bot);

        return "makebot";
    }

    @PostMapping("/makebot")
    public String makebotpost(Bot bot, Model model) {
        bot = botServ.save(bot);
        model.addAttribute("bot", bot);
        return "makebot_submitted";
    }
}
