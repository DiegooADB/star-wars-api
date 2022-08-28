package me.diego.starwarsapi.model.swapi;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanetResponse {
    private List<String> films;
}
