package me.diego.starwarsapi.services;

import me.diego.starwarsapi.domain.Planet;
import me.diego.starwarsapi.repositories.PlanetRepository;
import me.diego.starwarsapi.util.PlanetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetService {
    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    PlanetRequest planetRequest;

    public List<Planet> listAll() {
        return planetRepository.findAll();
    }

    public Planet save(Planet planet) {
        planet.setApparitions(planetRequest.getNumberFilms(planet.getName()));

        return planetRepository.save(planet);
    }
}
