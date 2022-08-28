package me.diego.starwarsapi.util;

import me.diego.starwarsapi.domain.Planet;
import me.diego.starwarsapi.model.swapi.PlanetResponse;

import java.util.List;

public class PlanetCreator {
    public static Planet createPlanetWithId() {
        return Planet.builder()
                .id(1L)
                .name("PlanetTest")
                .climate("temperate")
                .terrain("mountains")
                .apparitions(3)
                .build();
    }

    public static PlanetResponse createPlanetResponse() {
        return PlanetResponse.builder()
                .films(List.of(
                        "https://swapi.dev/api/films/1/",
                        "https://swapi.dev/api/films/3/",
                        "https://swapi.dev/api/films/4/",
                        "https://swapi.dev/api/films/5/",
                        "https://swapi.dev/api/films/6/"
                ))
                .build();
    }

    public static Planet createPlanetWithoutId() {
        return Planet.builder()
                .name("PlanetTest")
                .climate("temperate")
                .terrain("mountains")
                .apparitions(3)
                .build();
    }
}
