package me.diego.starwarsapi.controller;

import me.diego.starwarsapi.domain.Planet;
import me.diego.starwarsapi.service.PlanetService;
import me.diego.starwarsapi.util.PlanetCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class PlanetControllerTest {

    @InjectMocks
    private PlanetController planetController;

    @Mock
    private PlanetService planetService;

    @BeforeEach
    void setUp() {
        PageImpl<Planet> planetPage = new PageImpl<>(List.of(PlanetCreator.createPlanetWithId()));
        BDDMockito.when(planetService.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(planetPage);

        BDDMockito.when(planetService.save(ArgumentMatchers.any(Planet.class)))
                .thenReturn(PlanetCreator.createPlanetWithId());

        BDDMockito.when(planetService.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(PlanetCreator.createPlanetWithId()));

        BDDMockito.when(planetService.findByIdOrElseThrowsResponseStatusException(ArgumentMatchers.anyLong()))
                .thenReturn(PlanetCreator.createPlanetWithId());

        BDDMockito.doNothing().when(planetService).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("listAll returns list of planets inside a page when successful")
    void listAll_ReturnsListOfPlanetsInsideAPage_WhenSuccessful() {
        String planetExpectedName = PlanetCreator.createPlanetWithId().getName();
        Page<Planet> planets = planetController.listAll(PageRequest.of(1, 1)).getBody();

        Assertions.assertThat(planets)
                .isNotNull();

        Assertions.assertThat(planets.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(planets.toList().get(0).getName()).isEqualTo(planetExpectedName);
    }

    @Test
    @DisplayName("save returns planet when successful")
    void save_ReturnsPlanet_WhenSuccessful() {
        Planet planetToSave = PlanetCreator.createPlanetWithoutId();

        Planet planetSaved = planetController.save(planetToSave).getBody();

        Assertions.assertThat(planetSaved)
                .isNotNull()
                .isEqualTo(PlanetCreator.createPlanetWithId());

        Assertions.assertThat(planetSaved.getApparitions())
                .isEqualTo(planetToSave.getApparitions());
    }

    @Test
    @DisplayName("FindByName returns list of planets when successful")
    void findByName_ReturnsListOfPlanets_WhenSuccessful() {
        String planetExpected = PlanetCreator.createPlanetWithId().getName();

        List<Planet> planetList = planetController.findByName("PlanetTest").getBody();

        Assertions.assertThat(planetList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(planetList.get(0).getName()).isEqualTo(planetExpected);
    }

    @Test
    @DisplayName("findByIdOrElseThrowsResponseStatusException returns a planet when successful")
    void findByIdOrElseThrowsResponseStatusException_ReturnsAPlanet_WhenSuccessful() {
        long planetExpected = PlanetCreator.createPlanetWithId().getId();

        Planet planetFounded = planetController.findById(1L).getBody();

        Assertions.assertThat(planetFounded)
                .isNotNull();

        Assertions.assertThat(planetFounded.getId()).isEqualTo(planetExpected);
    }

    @Test
    @DisplayName("delete removes planet when successful")
    void delete_RemovesPlanet_WhenSuccessful() {
        ResponseEntity<Void> deleteResponse = planetController.delete(1L);

        Assertions.assertThatCode(() -> planetController.delete(1L))
                .doesNotThrowAnyException();

        Assertions.assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}