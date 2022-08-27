package me.diego.starwarsapi.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PlanetRequest {
    public Integer getNumberFilms(String name) {
        Mono<String> retrieve = WebClient.create().get()
                .uri("https://swapi.dev/api/planets?search=%s".formatted(name))
                .retrieve()
                .bodyToMono(String.class);

        String block = retrieve.block();

        JsonParser jsonParser = new JsonParser();
        JsonElement parse = jsonParser.parse(block);

        JsonObject obj = parse.getAsJsonObject();
        JsonArray planetResponse = obj.get("results").getAsJsonArray();

        if(planetResponse.size() == 0) return 0;

        JsonElement planet = planetResponse.get(0);

        JsonObject asJsonObject = planet.getAsJsonObject();
        JsonArray films = asJsonObject.getAsJsonArray("films");
        return films.size();
    }
}
