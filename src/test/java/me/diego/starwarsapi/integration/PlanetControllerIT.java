package me.diego.starwarsapi.integration;

import me.diego.starwarsapi.domain.Planet;
import me.diego.starwarsapi.repository.PlanetRepository;
import me.diego.starwarsapi.util.PlanetCreator;
import me.diego.starwarsapi.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlanetControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PlanetRepository planetRepository;

    @Test
    @DisplayName("listAll returns list of planets inside a page when successful")
    void listAll_ReturnsListOfPlanetsInsideAPage_WhenSuccessful() {
        Planet savedPlanet = planetRepository.save(PlanetCreator.createPlanetWithoutId());

        String expectedName = savedPlanet.getName();

        PageableResponse<Planet> planetList = testRestTemplate.exchange("/planets", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<Planet>>() {
                }).getBody();

        Assertions.assertThat(planetList)
                .isNotNull();

        Assertions.assertThat(planetList.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(planetList.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("save returns planet when successful")
    void save_ReturnsPlanet_WhenSuccessful() {
        Planet planetToBeSaved = PlanetCreator.createPlanetToBeSaved();

        ResponseEntity<Planet> planetResponseEntity = testRestTemplate
                .postForEntity("/planets", planetToBeSaved, Planet.class);

        Assertions.assertThat(planetResponseEntity)
                .isNotNull();

        Assertions.assertThat(planetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(planetResponseEntity.getBody()).isNotNull();

        Assertions.assertThat(planetResponseEntity.getBody().getId()).isNotNull();

        Assertions.assertThat(planetResponseEntity.getBody().getApparitions())
                .isEqualTo(5);
    }

    @Test
    @DisplayName("FindByName returns list of planets when successful")
    void findByName_ReturnsListOfPlanets_WhenSuccessful() {
        Planet planetExpected = planetRepository.save(PlanetCreator.createPlanetWithoutId());

        String expectedName = planetExpected.getName();

        String url = "/planets?name=%s".formatted(expectedName);

        List<Planet> animeList = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Planet>>() {
                }).getBody();

        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns a planet when successful")
    void findById_ReturnsAPlanet_WhenSuccessful() {
        Planet planetExpected = planetRepository.save(PlanetCreator.createPlanetWithoutId());

        Long expectedId = planetExpected.getId();

        Planet planet = testRestTemplate.getForObject("/planets/{id}", Planet.class, expectedId);

        Assertions.assertThat(planet).isNotNull();

        Assertions.assertThat(planet.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findById returns ResponseStatusException when not found")
    void findById_ReturnsResponseStatusException_WhenNotFound() {
        ResponseEntity<Planet> responseEntity = testRestTemplate
                .exchange("/planets/{id}", HttpMethod.GET, null, Planet.class, 1L);

        Assertions.assertThat(responseEntity).isNotNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    @DisplayName("delete removes planet when successful")
    void delete_RemovesPlanet_WhenSuccessful() {
        Planet savedPlanet = planetRepository.save(PlanetCreator.createPlanetWithoutId());

        ResponseEntity<Void> planetDeleted = testRestTemplate
                .exchange("/planets/{id}", HttpMethod.DELETE, null, Void.class, savedPlanet.getId());

        Assertions.assertThat(planetDeleted).isNotNull();

        Assertions.assertThat(planetDeleted.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
