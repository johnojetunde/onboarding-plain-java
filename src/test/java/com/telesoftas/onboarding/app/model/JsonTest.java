package com.telesoftas.onboarding.app.model;

import static java.util.Collections.singletonMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonGenerator;

class JsonTest {

    @Test
    void create_and_write() {
        Map<String, ?> config = singletonMap(JsonGenerator.PRETTY_PRINTING, true);
        JsonBuilderFactory factory = Json.createBuilderFactory(config);
        JsonObject value = factory.createObjectBuilder()
            .add("firstName", "John")
            .add("lastName", "Smith")
            .add("age", 25)
            .add("phoneNumber", factory.createArrayBuilder()
                .add(factory.createObjectBuilder()
                    .add("type", "home")
                    .add("number", "212 555-1234"))
                .add(factory.createObjectBuilder()
                    .add("type", "fax")
                    .add("number", "646 555-4567")))
            .build();
        StringWriter buffer = new StringWriter();

        Json.createWriterFactory(config).createWriter(buffer).writeObject(value);

        Assertions.assertEquals(
            "{\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Smith\",\n" +
                "    \"age\": 25,\n" +
                "    \"phoneNumber\": [\n" +
                "        {\n" +
                "            \"type\": \"home\",\n" +
                "            \"number\": \"212 555-1234\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"fax\",\n" +
                "            \"number\": \"646 555-4567\"\n" +
                "        }\n" +
                "    ]\n" +
                "}",
            buffer.toString().trim()
        );
    }

    @Test
    void read() {
        JsonReader reader = Json.createReader(new StringReader("{\"status\":\"ok\"}"));

        Assertions.assertEquals("ok", reader.readObject().getString("status"));
    }
}
