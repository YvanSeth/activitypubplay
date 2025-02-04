package dev.activitypub.activitypubbot;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * There is so much to be derived and generated when serialising a Bot
 * instanced for ActivityPub purposes it seems neater to just put all that in
 * one place than try to override each individual thing. FIXME: But I'm not
 * convinced this is the right approach, there's far too much manual specifying
 * and building of strings. So I need to look deeper into better options for
 * JSON generation. Part of the problem also is the need to mix in
 * configuration/property values to generate some fields. So there's a larger
 * design question to be resolved here.
 *
 * As per: https://www.baeldung.com/jackson-custom-serialization
 */
public class BotAPActorJsonSerializer extends StdSerializer<Bot> {

    public BotAPActorJsonSerializer() {
        this(null);
    }

    public BotAPActorJsonSerializer(final Class<Bot> t) {
        super(t);
    }

    @Override
    public final void serialize(final Bot bot, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {
        // FIXME: these are basically server settings and should come from properties?
        // or should they be in the database... as general settings, or as part of the user?
        // what are the implications of the domain, can it change and things still be valid?
        String domain = "springbot.seth.id.au";
        String scheme = "https";

        jgen.writeStartObject();

        // FIXME: got a whole bunch of string values here that should be refactored up or something
        jgen.writeArrayFieldStart("@context");
        jgen.writeString("https://www.w3.org/ns/activitystreams");
        jgen.writeString("https://w3id.org/security/v1");
        jgen.writeEndArray();

        // FIXME: we at least need some sort of "URI generator"
        String id = scheme + "://" + domain + "/users/" + bot.getUsername();
        jgen.writeStringField("id", id );
        jgen.writeStringField("inbox", id + "/inbox" );
        jgen.writeStringField("outbox", id + "/outbox" );

        jgen.writeStringField("preferredUsername", bot.getUsername());
        jgen.writeStringField("name", bot.getName());
        jgen.writeStringField("type", bot.getType());
        jgen.writeStringField("summary", bot.getSummary());

        jgen.writeBooleanField("manuallyApproveFollowers", bot.isManuallyApproveFollowers());
        jgen.writeBooleanField("indexable", bot.isIndexable());

        jgen.writeFieldName("publicKey");
            jgen.writeStartObject();
            jgen.writeStringField("id", id + "#main-key" );
            jgen.writeStringField("owner", id );
            jgen.writeStringField("publicKeyPem", bot.getPublicKeyPEMString());
            jgen.writeEndObject();
        jgen.writeEndObject();
    }
}


