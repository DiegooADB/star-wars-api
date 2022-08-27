package me.diego.starwarsapi.services;

import me.diego.starwarsapi.domain.Planet;
import me.diego.starwarsapi.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetService {
    @Autowired
    PlanetRepository planetRepository;

    public List<Planet> listAll() {
        return planetRepository.findAll();
    }

    public Planet save(Planet planet) {
        return planetRepository.save(planet);
    }
}
