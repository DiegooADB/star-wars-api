package me.diego.starwarsapi.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PlanetRequest {
    public Integer getNumberFilms() {
        Mono<String> retrieve = WebClient.create().get()
                .uri("https://swapi.dev/api/planets?search=Tatooine")
                .retrieve()
                .bodyToMono(String.class);

        String block = retrieve.block();

        JsonParser jsonParser = new JsonParser();
        JsonElement parse = jsonParser.parse(block);

        JsonObject obj = parse.getAsJsonObject();
        JsonElement planet = obj.get("results").getAsJsonArray().get(0);

        JsonObject asJsonObject = planet.getAsJsonObject();
        JsonArray films = asJsonObject.getAsJsonArray("films");
        return films.size();
    }
}
