package me.diego.starwarsapi.controllers;

import me.diego.starwarsapi.domain.Planet;
import me.diego.starwarsapi.services.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<Planet>> findAll(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(planetService.listAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Planet> save(@RequestBody Planet planet) {
        return new ResponseEntity<>(planetService.save(planet), HttpStatus.CREATED);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<Planet>> findByName(@RequestParam String name) {
        return new ResponseEntity<>(planetService.findByName(name), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> findById(@PathVariable Long id) {
        return new ResponseEntity<>(planetService.findByIdOrElseThrowsResponseStatusException(id), HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        planetService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
