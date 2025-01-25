package dev.activitypub.activitypubbot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class BotController {

    @Autowired
    private BotRepo botRepo;

    @GetMapping("/viewbot")
    public String listAll(Model model) {
        System.out.println("WebHandler::viewbot");

        List<Bot> botlist = botRepo.findAll();
        model.addAttribute("bots", botlist);

        return "viewbot";
    }

    @GetMapping("/makebot")
    public String makebotget(Model model) {
        System.out.println("WebHandler::makebot");

        Bot bot = new Bot();
        model.addAttribute("bot", bot);

        return "makebot";
    }

    @PostMapping("/makebot")
    public String makebotpost(@ModelAttribute("bot") Bot bot) {
        System.out.println(bot);

        botRepo.save(bot);

        return "makebot_submitted";
    }
}
