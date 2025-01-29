package dev.activitypub.activitypubbot;

public class BotNotFoundByUsernameException extends RuntimeException {
    public BotNotFoundByUsernameException() {
        super();
    }

    public BotNotFoundByUsernameException(String message) {
        super(message);
    }
}
