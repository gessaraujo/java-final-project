package com.cbfacademy.apiassessment.Service;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Dto.AddSeasonDto;
import com.cbfacademy.apiassessment.Model.Entity.Content.Serie;
import com.cbfacademy.apiassessment.Model.Repository.GenreRepository;
import com.cbfacademy.apiassessment.Model.Repository.SerieRepository;
import com.cbfacademy.apiassessment.Model.Service.SerieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SerieServiceTest {
    @Mock
    private SerieRepository serieRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private SerieService serieService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void createSerie_ValidSerie_Success() throws FileNotFoundException, CustomException {
        Serie serie = new Serie();
        serie.setTitle("Title");
        serie.setGenre(null);

        Assertions.assertDoesNotThrow(() -> serieService.create(serie));

        Mockito.verify(serieRepository, times(1)).save(serie);
    }

    @Test
    public void findAll_ReturnsListOfSeries() throws FileNotFoundException, CustomException {
        List<Serie> series = Arrays.asList(new Serie(), new Serie());

        when(serieRepository.findAll()).thenReturn(series);

        Assertions.assertEquals(series, serieService.findAll());
    }

    @Test
    public void updateSerie_ValidSerie_Success() throws FileNotFoundException, CustomException {
        Serie serie = new Serie();
        serie.setTitle("Title");
        serie.setGenre(null);

        Assertions.assertDoesNotThrow(() -> serieService.update(serie));

        Mockito.verify(serieRepository, times(1)).update(serie);
    }

    @Test
    public void deleteSerie_ValidId_Success() throws CustomException {
        String id = UUID.randomUUID().toString();

        Assertions.assertDoesNotThrow(() -> serieService.delete(id));

        Mockito.verify(serieRepository, times(1)).delete(UUID.fromString(id));
    }

    @Test
    public void addSeason_ValidDto_Success() throws FileNotFoundException, CustomException {
        AddSeasonDto dto = new AddSeasonDto();
        dto.setSerieId(UUID.randomUUID().toString());
        dto.setSeasonId(UUID.randomUUID().toString());

        Assertions.assertDoesNotThrow(() -> serieService.addSeason(dto));

        Mockito.verify(serieRepository, times(1)).addSeason(dto);
    }

    @Test
    public void removeSeason_ValidDto_Success() throws CustomException, FileNotFoundException {
        AddSeasonDto dto = new AddSeasonDto();
        dto.setSerieId(UUID.randomUUID().toString());
        dto.setSeasonId(UUID.randomUUID().toString());

        Assertions.assertDoesNotThrow(() -> serieService.removeSeason(dto));

        Mockito.verify(serieRepository, times(1)).removeSeason(dto);
    }

    @Test
    public void validateSerie_NullTitle_ThrowsCustomException() {
        Serie serie = new Serie();
        serie.setTitle(null);

        Assertions.assertThrows(CustomException.class, () -> serieService.create(serie));
    }

    @Test
    public void validateSerie_NullGenre_ThrowsCustomException() {
        Serie serie = new Serie();
        serie.setTitle("Title");

        Assertions.assertDoesNotThrow(() -> serieService.create(serie));
    }

    @Test
    public void validateAddSeason_EmptySerieId_ThrowsCustomException() {
        AddSeasonDto dto = new AddSeasonDto();
        dto.setSeasonId(UUID.randomUUID().toString());

        Assertions.assertThrows(CustomException.class, () -> serieService.addSeason(dto));
    }

    @Test
    public void validateAddSeason_EmptySeasonId_ThrowsCustomException() {
        AddSeasonDto dto = new AddSeasonDto();
        dto.setSerieId(UUID.randomUUID().toString());

        Assertions.assertThrows(CustomException.class, () -> serieService.addSeason(dto));
    }

    @Test
    public void validateRemoveSeason_EmptySerieId_ThrowsCustomException() {
        AddSeasonDto dto = new AddSeasonDto();
        dto.setSeasonId(UUID.randomUUID().toString());

        Assertions.assertThrows(CustomException.class, () -> serieService.removeSeason(dto));
    }

    @Test
    public void validateRemoveSeason_EmptySeasonId_ThrowsCustomException() {
        AddSeasonDto dto = new AddSeasonDto();
        dto.setSerieId(UUID.randomUUID().toString());

        Assertions.assertThrows(CustomException.class, () -> serieService.removeSeason(dto));
    }
}
