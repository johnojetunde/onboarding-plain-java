package com.telesoftas.onboarding.googlebook.mapper;


import com.telesoftas.onboarding.googlebook.model.GoogleBook;
import lombok.experimental.UtilityClass;

import javax.json.*;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class HttpEntityMapper {

    public List<GoogleBook> toBooks(InputStream entity) {
        try (final JsonReader jsonReader = Json.createReader(entity)) {
            JsonObject object = jsonReader.readObject();

            JsonArray items = object.getJsonArray("items");

            return items.getValuesAs(b -> toBook(b.asJsonObject()));
        }
    }

    private GoogleBook toBook(JsonObject jsonObject) {
        JsonObject volumeInfo = jsonObject.getJsonObject("volumeInfo");
        return GoogleBook.builder()
            .title(volumeInfo.getString("title", null))
            .description(volumeInfo.getString("description", ""))
            .publisher(volumeInfo.getString("publisher", ""))
            .pageCount(volumeInfo.getInt("pageCount", 0))
            .authors(volumeInfo.containsKey("authors") ?
                volumeInfo.getJsonArray("authors").stream().map(JsonValue::toString).collect(Collectors.toList())
                : Collections.emptyList())
            .build();
    }


}
