package dev.activitypub.activitypubbot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Started out with a generic "Hello World" REST API controller,
 * extending this to handle the basic ActivityPub implementation.
 */
@RestController
public class HomeController {

    @Autowired
    public APProperties apProps;

	// FIXME: just playing with working out app properties access here
	@GetMapping("/key")
	public String key() {
		return apProps.getKeyFilePath();
	}

	/**
	 * Keeping / mysterious...
	 */
	@GetMapping("/")
	public String index() {
		return "Parsnip!";
	}

	/**
	 * Really just an alias to /user/springbot
	 * TODO: how to auto-map @<user> to /users/<user>
	 */
	@GetMapping("/@springbot")
	public String atactor() {
		return this.actor();
	}

	/**
	 * Access the bot/user - ultimately this should check the database for
	 * /user/<user> to pull out the relevant data.
	 */
	@GetMapping("/users/springbot")
	public String actor() {
		// TODO: this needs some sort of object wrapper or template, and data in database
		return """
{
	"@context": [
		"https://www.w3.org/ns/activitystreams",
		"https://w3id.org/security/v1"
	],

	"id": "https://springbot.seth.id.au/users/springbot",
	"url": "https://springbot.seth.id.au/@springbot",
	"inbox": "https://springbot.seth.id.au/users/springbot/inbox",
	"type": "Person",
	"preferredUsername": "springbot",
	"name": "Spring Bot",
	"manuallyApproveFollowers": false,
	"indexable": false,
	"published": "2025-01-24T00:00:00Z",
	"summary": "<p>A bot written using Java/Spring</p>",
	"publicKey": {
		"id": "https://springbot.seth.id.au/users/springbot#main-key",
		"owner": "https://springap.seth.id.au/users/springbot",
		"publicKeyPem": "-----BEGIN PUBLIC KEY-----...-----END PUBLIC KEY-----"
	}
}""";
	}
}
