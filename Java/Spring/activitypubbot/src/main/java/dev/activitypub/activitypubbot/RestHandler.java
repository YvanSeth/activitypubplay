package dev.activitypub.activitypubbot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Here we handle any JSON/REST requests, which is how ActivityPub instances talk to each other.
 */
@RestController
@RequestMapping( headers = "accept=application/json" )
public class RestHandler {

	/**
	 * Really just an alias to /user/springbot
	 * TODO: how to auto-map @<user> to /users/<user>
	 */
	@RequestMapping(value = "/@springbot", headers = "accept=application/json")
	public String atactor() {
		return this.actor();
	}

	/**
	 * Access the bot/user - ultimately this should check the database for
	 * /user/<user> to pull out the relevant data.
	 */
	@RequestMapping(value = "/users/springbot", headers = "accept=application/json")
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
		"publicKeyPem": "-----BEGIN PUBLIC KEY-----\\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0A5W6M6b+3meJAU0/Fki\\nkMSrEZ6EEThAv4NmyCeDlRbmFQWbh5rWtb69TxkGkkiSFNM3sgg+RSW44Ehn10mL\\nTptfk6oSWFnFHw9MPxmwlWm1Xw8zmp2OMUlI82w11PECFdITJw/1HW73JSVQYfFq\\nWo9rD6nI9G3LPpAB16015NJ9hyeMvz5RA9p9UE540q0l5iJD/l7bxCjHglOQInQX\\neCiR2ErzQSVq3AMhBehoP7HuhKjs8swi8dOgjO3sawqxUyv2+lkesFD2rvxCcXRO\\nBkg/Y7nmJSEcqtcmKYQdObPCIt/wCZNAihJz7dwnGKLE2+JJqPZMer9fAj077OkQ\\neQIDAQAB\\n-----END PUBLIC KEY-----"
	}
}""";
	}

	/**
	 * Webfinger the user...
	 */
	@GetMapping("/.well-known/webfinger")
	public String webfinger() {
		return """
{
	"subject": "acct:springbot@springbot.seth.id.au",

	"links": [
		{
			"rel": "self",
			"type": "application/activity+json",
			"href": "https://springbot.seth.id.au/users/springbot"
		}
	]
}""";
	}
}
