package dev.activitypub.activitypubbot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus;
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

    /**
     * Send a 404 page for bots if someone tries to access a bot that doesn't exist.
     */
    @ExceptionHandler(value = BotNotFoundByUsernameException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleBotNotFoundByUsernameException(BotNotFoundByUsernameException ex) {
        ModelAndView mav = new ModelAndView("user_not_found");
        mav.addObject("message", ex.getLocalizedMessage()); 
        return mav;
    }

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

    @PostMapping("/viewbot/{username}")
    public String viewbotpost(String posttext, @PathVariable String username, Model model) {
        log.info("BotController::postpost: " + username + ", content: " + posttext);

        Bot bot = botServ.getBotByUsername(username);
        model.addAttribute("bot", bot);
        return "viewbot";
    }
}
