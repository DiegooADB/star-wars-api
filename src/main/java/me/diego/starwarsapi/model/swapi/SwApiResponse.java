package me.diego.starwarsapi.model.swapi;

import lombok.Getter;

import java.util.List;

@Getter
public class SwApiResponse {
    private List<PlanetResponse> results;
}
