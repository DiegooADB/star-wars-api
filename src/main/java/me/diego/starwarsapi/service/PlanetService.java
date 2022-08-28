package me.diego.starwarsapi.service;

import me.diego.starwarsapi.domain.Planet;
import me.diego.starwarsapi.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlanetService {
    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    SwApiService swApiService;

    public Page<Planet> listAll(Pageable pageable) {
        return planetRepository.findAll(pageable);
    }

    public Planet save(Planet planet) {
        planet.setApparitions(swApiService.getFilms(planet.getName()));

        return planetRepository.save(planet);
    }

    public List<Planet> findByName(String name) {
        return planetRepository.findByName(name);
    }

    public Planet findByIdOrElseThrowsResponseStatusException(Long id){
        return planetRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Planet not found"));
    }

    public void delete(Long id) {
        planetRepository.delete(findByIdOrElseThrowsResponseStatusException(id));
    }
}
