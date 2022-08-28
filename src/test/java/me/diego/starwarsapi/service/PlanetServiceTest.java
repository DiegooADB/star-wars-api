package me.diego.starwarsapi.service;

import me.diego.starwarsapi.domain.Planet;
import me.diego.starwarsapi.repository.PlanetRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith({SpringExtension.class})
class PlanetServiceTest {
    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRepository planetRepository;

    @Mock
    private SwApiService swApiService;

    @BeforeEach
    void setUp() {
        PageImpl<Planet> planetPage = new PageImpl<>(List.of(PlanetCreator.createPlanetWithId()));
        BDDMockito.when(planetRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(planetPage);

        BDDMockito.when(swApiService.getFilms(ArgumentMatchers.anyString()))
                .thenReturn(3);

        BDDMockito.when(planetRepository.save(ArgumentMatchers.any(Planet.class)))
                .thenReturn(PlanetCreator.createPlanetWithId());

        BDDMockito.when(planetRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(PlanetCreator.createPlanetWithId()));

        BDDMockito.when(planetRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(PlanetCreator.createPlanetWithId()));

        BDDMockito.doNothing().when(planetRepository).delete(ArgumentMatchers.any(Planet.class));
    }

    @Test
    @DisplayName("listAll returns list of planets inside a page when successful")
    void listAll_ReturnsListOfPlanetsInsideAPage_WhenSuccessful() {
        String planetExpectedName = PlanetCreator.createPlanetWithId().getName();

        Page<Planet> planets = planetService.listAll(PageRequest.of(1, 1));


        Assertions.assertThat(planets)
                .isNotNull();

        Assertions.assertThat(planets)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(planets.toList().get(0).getName()).isEqualTo(planetExpectedName);
    }

    @Test
    @DisplayName("save returns planet when successful")
    void save_ReturnsPlanet_WhenSuccessful() {
        Planet planetToSave = PlanetCreator.createPlanetWithoutId();

        Planet planetSaved = planetService.save(planetToSave);

        Assertions.assertThat(planetSaved)
                .isEqualTo(PlanetCreator.createPlanetWithId());

        Assertions.assertThat(planetSaved.getApparitions())
                .isEqualTo(planetToSave.getApparitions());
    }

    @Test
    @DisplayName("FindByName returns list of planets when successful")
    void findByName_ReturnsListOfPlanets_WhenSuccessful() {
        String planetExpected = PlanetCreator.createPlanetWithId().getName();

        List<Planet> planetList = planetService.findByName("PlanetTest");

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

        Planet planetFounded = planetService.findByIdOrElseThrowsResponseStatusException(1L);

        Assertions.assertThat(planetFounded)
                .isNotNull();

        Assertions.assertThat(planetFounded.getId()).isEqualTo(planetExpected);
    }

    @Test
    @DisplayName("findByIdOrElseThrowsResponseStatusException throws ResponseStatusException when not found")
    void findByIdOrElseThrowsResponseStatusException_ThrowsResponseStatusException_WhenNotFound() {
        BDDMockito.when(planetRepository.findById(ArgumentMatchers.anyLong()))
                        .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> planetService.findByIdOrElseThrowsResponseStatusException(1L));
    }

    @Test
    @DisplayName("delete removes planet when successful")
    void delete_RemovesPlanet_WhenSuccessful() {
        Assertions.assertThatCode(() -> planetService.delete(1L))
                .doesNotThrowAnyException();
    }
}