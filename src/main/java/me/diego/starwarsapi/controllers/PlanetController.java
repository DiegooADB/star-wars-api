package me.diego.starwarsapi.controllers;

import me.diego.starwarsapi.domain.Planet;
import me.diego.starwarsapi.services.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "planets")
public class PlanetController {
    @Autowired
    PlanetService planetService;

    @GetMapping
    public ResponseEntity<List<Planet>> findAll() {
        return new ResponseEntity<>(planetService.listAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Planet> save(@RequestBody Planet planet) {
        return new ResponseEntity<>(planetService.save(planet), HttpStatus.CREATED);
    }
}
