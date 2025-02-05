package dev.activitypub.activitypubbot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Here we handle any JSON/REST requests, which is how ActivityPub instances talk to each other.
 */
@Slf4j
@RestController
public class RestHandler {

    @Autowired
    private BotService botServ;

	/**
	 * Really just an alias to /user/<username>
	 */
	@GetMapping(value = "/@{username}", produces = "application/activity+json") // content type based on Masto request
	public Bot atactor(@PathVariable String username) {
		return this.actor( username );
	}

	/**
	 * Get the bot/user 'actor' data response
	 */
	@GetMapping(value = "/users/{username}", produces = "application/activity+json") // content type based on Masto request
	public Bot actor(@PathVariable String username) {

        Bot bot = botServ.getBotByUsername( username );
        if( bot == null ) {
            //return new ResponseEntity<>("These are not the droids you are looking for.", HttpStatus.NOT_FOUND);
        }
        return bot;
	}

	/**
	 * Webfinger the user...
	 */
	@GetMapping(value = "/.well-known/webfinger", produces = "application/jrd+json") // content type based on Masto request
	public String webfinger(@RequestParam("resource") String resource) throws JsonProcessingException {

        // resource should be of the form: acct:<username>@<domain> 
        // so this should be robustly checked, but for now just yoink out the username
        int colonPos = resource.indexOf(":");
        int atPos = resource.indexOf("@");
        if ( colonPos < 0 || atPos < 0 || atPos < colonPos ) {
            //return new ResponseEntity<>("Incorrect query format",HttpStatus.BAD_REQUEST);
        }
        String username = resource.substring(colonPos + 1, atPos);
        Bot bot = botServ.getBotByUsername( username );
        if( bot == null ) {
            //return new ResponseEntity<>("These are not the droids you are looking for.", HttpStatus.NOT_FOUND);
        }

        // TODO: So here's a whole other way of generating custom JSON as
        // compared to the BotAPActorJSONSerializer approach, should perhaps
        // settle on one style of doing it (if we're going to do it either way
        // in the end.)
        Map<String, Object> response = Map.of(
                "subject", botServ.getWebfingerSubject( bot ),
                "links", List.of(
                    Map.of(
                        "rel", "self",
                        "type", "application/activity+json",
                        "href", botServ.getId( bot )
                        )
                    )
                );
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString( response );
    }


	/**
	 * 
	 */
	@PostMapping(value = "/users/{username}/inbox", produces = "application/activity+json") // content type based on Masto request
	public String inboxpost(@PathVariable String username, @RequestBody Map<String, Object> postdata) {
        log.info("RestHandler::inboxpost: " + username + " map: " + postdata.toString());
        Bot bot = botServ.getBotByUsername( username );
        if( bot == null ) {
            //return new ResponseEntity<>("These are not the droids you are looking for.", HttpStatus.NOT_FOUND);
        }
        if( !postdata.containsKey("type") ) {
            // no request type, so we think we've been sent junk
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No request 'type' specified.");
        }

        // What sort of inbox post is this... we probably eventually want the terms
        // here to be via some sort of request type handler registry. Perhaps an
        // ActivityPubActivity interface defining some consumer of JSON...
        switch ((String)postdata.get("type")) {
            case "Follow":
                // process a follow request, a job for BotService? FollowService? How to represent a "follow"...
                // Note: https://w3c.github.io/activitypub/#followers
                return "{ follow: \"the leader\"; }";
            case "Create":
            case "Update":
            case "Delete":
            case "Accept":
            case "Reject":
            case "Add":
            case "Remove":
            case "Announce":
            case "Undo":
                // not implemented:
                throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Nope!");
            default:
                // unprocessable content
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Unknown 'type': " + postdata.get("type") );
        }
	}

}
