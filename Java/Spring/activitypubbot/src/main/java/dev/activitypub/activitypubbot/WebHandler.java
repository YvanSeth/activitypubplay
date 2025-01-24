package dev.activitypub.activitypubbot;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Here we handle all non-JSON/REST requests - i.e. the normal "web" view
 */
@Controller
public class WebHandler {
    
     /**
      * handle requests for our "actor" - this presents the web/html view of
      * the bot
      */	
    @RequestMapping("/@springbot")
    public String atactor() {
        System.out.println("WebHandler::atactor");
        return this.actor();
    }
    @RequestMapping("/users/springbot")
    public String actor() {
        System.out.println("WebHandler::actor");
        return "index"; // just flinging out the index page for now
    }

    /**
     * our index page
     */
    @RequestMapping("/")
    public String root() {
        System.out.println("WebHandler::root");
        return "index";
    }
}
