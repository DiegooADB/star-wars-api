package me.diego.starwarsapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public
class PlanetResponse {
    private List<String> films;
}
