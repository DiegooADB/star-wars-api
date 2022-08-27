package me.diego.starwarsapi.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SwApiResponse {
    private List<PlanetResponse> results;
}
