package me.diego.starwarsapi.repository;

import me.diego.starwarsapi.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
    List<Planet> findByName(String name);
}
