package dev.activitypub.activitypubbot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public String index() {
		return "Parsnip!";
	}

	@GetMapping("/actor")
	public String actor() {
		return """
{
	"@context": [
		"https://www.w3.org/ns/activitystreams",
		"https://w3id.org/security/v1"
	],

	"id": "https://springap.seth.id.au/actor",
	"type": "Bot",
	"preferredUsername": "SpringBot
	"inbox": "https://springap.seth.id.au/inbox",

	"publicKey": {
		"id": "https://springap.seth.id.au/actor#main-key",
		"owner": "https://springap.seth.id.au/actor",
		"publicKeyPem": "-----BEGIN PUBLIC KEY-----...-----END PUBLIC KEY-----"
	}
}""";
	}
}
