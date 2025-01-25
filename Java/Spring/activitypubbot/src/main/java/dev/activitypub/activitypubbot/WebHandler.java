package dev.activitypub.activitypubbot;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

/**
 * General web requst implementations
 */
@Controller
@Slf4j
public class WebHandler {
    
    @Autowired
    private APProperties apProps;

     /**
      * handle requests for our "actor" - this presents the web/html view of
      * the bot
      */	
    @RequestMapping("/@springbot")
    public String atactor() {
        log.info("WebHandler::atactor");
        return this.actor();
    }
    @RequestMapping("/users/springbot")
    public String actor() {
        log.info("WebHandler::actor");
        return "index"; // just flinging out the index page for now
    }

    /**
     * our index page
     */
    @RequestMapping("/")
    public String root() {
        log.info("WebHandler::root");
        return "index";
    }
/*
    // TODO: presumably there is some way to map things like /<string> to capture string and attempt to resolve template
    @RequestMapping("/viewbot")
    public String viewbot() {
        log.info("WebHandler::viewbot");
        return "viewbot";
    }
    @RequestMapping("/makebot")
    public String makebot() {
        log.info("WebHandler::makebot");
        return "makebot";
    }
*/
}
