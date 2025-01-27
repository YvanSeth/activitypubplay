package dev.activitypub.activitypubbot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Here we handle any JSON/REST requests, which is how ActivityPub instances talk to each other.
 */
@RestController
@RequestMapping( headers = "accept=application/json" )
public class RestHandler {

    @Autowired
    private BotService botServ;

    @Autowired
    @Qualifier("messageTemplateEngine")
    protected SpringTemplateEngine jsonTemplater;

	/**
	 * Really just an alias to /user/<username>
	 */
	@RequestMapping(value = "/@{username}", produces = "application/activity+json") // content type based on Masto request
	public String atactor(@PathVariable String username) {
		return this.actor( username );
	}

	/**
	 * Access the bot/user
	 */
	@RequestMapping(value = "/users/{username}", produces = "application/activity+json") // content type based on Masto request
	public String actor(@PathVariable String username) {

        Bot bot = botServ.getBotByUsername( username );
        if( bot == null ) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "These are not the droids you are looking for" );
        }
		// TODO: kludgetown... we should generate JSON programmatically rather than templates
        final Context ctx = new Context();
        ctx.setVariable("bot", bot);
        final String json = jsonTemplater.process("json/actor", ctx);
        return json;
	}

	/**
	 * Webfinger the user...
	 */
	@GetMapping(value = "/.well-known/webfinger", produces = "application/jrd+json") // content type based on Masto request
	public String webfinger(@RequestParam("resource") String resource) {

        // resource should be of the form: acct:<username>@<domain> 
        // so this should be robustly checked, but for now just yoink out the username
        String username = resource.substring(resource.indexOf(":") + 1, resource.indexOf("@"));
        if ( username == null) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Malformed" );
        }
        Bot bot = botServ.getBotByUsername( username );
        if( bot == null ) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "These are not the droids you are looking for" );
        }
		// TODO: kludgetown... we should generate JSON programmatically rather than templates
        final Context ctx = new Context();
        ctx.setVariable("bot", bot);
        final String json = jsonTemplater.process("json/webfinger", ctx);
        return json;
    }
}
