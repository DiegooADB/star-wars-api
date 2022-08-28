package me.diego.starwarsapi.service;

import lombok.RequiredArgsConstructor;
import me.diego.starwarsapi.model.swapi.SwApiResponse;
import me.diego.starwarsapi.model.swapi.PlanetResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class SwApiService {
    public int getFilms(String name) {
        ResponseEntity<SwApiResponse> response = WebClient
                .create("https://swapi.dev/api/planets?search=%s".formatted(name))
                .get()
                .retrieve()
                .toEntity(SwApiResponse.class).block();

        List<PlanetResponse> results = response.getBody().getResults();
        if (results.size() == 0) return 0;

        return results.get(0).getFilms().size();
    }
}
