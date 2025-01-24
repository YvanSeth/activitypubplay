package dev.activitypub.activitypubbot;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class MvcController {
     
    @RequestMapping("/@springbot")
    public String home() {
        System.out.println("Bottty McBotface...");
        return "index";
    }

    @RequestMapping("/")
    public String root() {
        System.out.println("Going home...");
        return "index";
    }
}
