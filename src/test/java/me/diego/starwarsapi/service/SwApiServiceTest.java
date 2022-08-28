package me.diego.starwarsapi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SwApiServiceTest {
    @InjectMocks
    private SwApiService swApiService;

    @Test
    @DisplayName("getFilms returns count when successful")
    void getFilms_ReturnsCount_WhenSuccessful() {
        int expectedNumber = 5;
        int response = swApiService.getFilms("Tatooine");
        Assertions.assertThat(response)
                .isEqualTo(expectedNumber);
    }
}