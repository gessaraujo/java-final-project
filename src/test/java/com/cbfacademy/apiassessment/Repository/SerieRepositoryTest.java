package com.cbfacademy.apiassessment.Repository;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Dto.AddSeasonDto;
import com.cbfacademy.apiassessment.Model.Entity.Content.Serie;
import com.cbfacademy.apiassessment.Model.Entity.Season;
import com.cbfacademy.apiassessment.Model.Repository.DataBaseRepository;
import com.cbfacademy.apiassessment.Model.Repository.Entities;
import com.cbfacademy.apiassessment.Model.Repository.SeasonRepository;
import com.cbfacademy.apiassessment.Model.Repository.SerieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SerieRepositoryTest {
    @Mock
    private DataBaseRepository repository;

    @Mock
    private SeasonRepository seasonRepository;

    @InjectMocks
    private SerieRepository serieRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void save_ValidSerie_Success() throws CustomException {
        Serie serie = new Serie();
        serie.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setSeries(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> serieRepository.save(serie));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void findAll_ReturnsListOfSeries() throws CustomException, FileNotFoundException {
        Entities entities = new Entities();
        entities.setSeries(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        List<Serie> series = serieRepository.findAll();

        Assertions.assertNotNull(series);
        Assertions.assertEquals(0, series.size());
    }

    @Test
    public void findById_ExistingId_ReturnsSerie() throws CustomException, FileNotFoundException {
        UUID id = UUID.randomUUID();

        Serie serie = new Serie();
        serie.setId(id);

        Entities entities = new Entities();
        entities.setSeries(new ArrayList<>());
        entities.getSeries().add(serie);

        when(repository.getAll()).thenReturn(entities);

        Serie foundSerie = Assertions.assertDoesNotThrow(() -> serieRepository.findById(id));

        Assertions.assertNotNull(foundSerie);
        Assertions.assertEquals(id, foundSerie.getId());
    }

    @Test
    public void update_ExistingSerie_Success() throws CustomException {
        Serie serie = new Serie();
        serie.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setSeries(new ArrayList<>());
        entities.getSeries().add(serie);

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> serieRepository.update(serie));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void delete_ExistingSerie_Success() throws CustomException {
        UUID id = UUID.randomUUID();

        Serie serie = new Serie();
        serie.setId(id);

        Entities entities = new Entities();
        entities.setSeries(new ArrayList<>());
        entities.getSeries().add(serie);

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> serieRepository.delete(id));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void addSeason_ValidDto_Success() throws CustomException, FileNotFoundException {
        UUID serieId = UUID.randomUUID();
        UUID seasonId = UUID.randomUUID();

        AddSeasonDto dto = new AddSeasonDto();
        dto.setSerieId(serieId.toString());
        dto.setSeasonId(seasonId.toString());

        Season season = new Season();
        season.setId(seasonId);

        Serie serie = new Serie();
        serie.setId(serieId);
        serie.setSeasonIds(new ArrayList<>());

        Entities entities = new Entities();
        entities.setSeries(new ArrayList<>());
        entities.getSeries().add(serie);

        when(seasonRepository.findById(seasonId)).thenReturn(season);
        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> serieRepository.addSeason(dto));

        Mockito.verify(seasonRepository, times(1)).findById(seasonId);
        Mockito.verify(repository, times(1)).saveAll(entities);
    }
}
