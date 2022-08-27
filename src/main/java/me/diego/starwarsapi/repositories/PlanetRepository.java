package me.diego.starwarsapi.repositories;

import me.diego.starwarsapi.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
}
