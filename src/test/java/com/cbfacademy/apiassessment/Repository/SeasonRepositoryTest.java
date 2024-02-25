package com.cbfacademy.apiassessment.Repository;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Dto.AddEpisodeDto;
import com.cbfacademy.apiassessment.Model.Entity.Episode;
import com.cbfacademy.apiassessment.Model.Entity.Season;
import com.cbfacademy.apiassessment.Model.Repository.DataBaseRepository;
import com.cbfacademy.apiassessment.Model.Repository.Entities;
import com.cbfacademy.apiassessment.Model.Repository.EpisodeRepository;
import com.cbfacademy.apiassessment.Model.Repository.SeasonRepository;
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
public class SeasonRepositoryTest {
    @Mock
    private DataBaseRepository repository;

    @Mock
    private EpisodeRepository episodeRepository;

    @InjectMocks
    private SeasonRepository seasonRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void save_ValidSeason_Success() throws CustomException {
        Season season = new Season();
        season.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setSeasons(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> seasonRepository.save(season));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void findAll_ReturnsListOfSeasons() throws CustomException, FileNotFoundException {
        Entities entities = new Entities();
        entities.setSeasons(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        List<Season> seasons = seasonRepository.findAll();

        Assertions.assertNotNull(seasons);
        Assertions.assertEquals(0, seasons.size());
    }

    @Test
    public void findById_ExistingId_ReturnsSeason() throws CustomException, FileNotFoundException {
        UUID id = UUID.randomUUID();

        Season season = new Season();
        season.setId(id);

        Entities entities = new Entities();
        entities.setSeasons(new ArrayList<>());
        entities.getSeasons().add(season);

        when(repository.getAll()).thenReturn(entities);

        Season foundSeason = Assertions.assertDoesNotThrow(() -> seasonRepository.findById(id));

        Assertions.assertNotNull(foundSeason);
        Assertions.assertEquals(id, foundSeason.getId());
    }

    @Test
    public void findById_NonExistingId_ReturnsNull() throws CustomException, FileNotFoundException {
        UUID id = UUID.randomUUID();

        Entities entities = new Entities();
        entities.setSeasons(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Season foundSeason = Assertions.assertDoesNotThrow(() -> seasonRepository.findById(id));

        Assertions.assertNull(foundSeason);
    }

    @Test
    public void update_ExistingSeason_Success() throws CustomException {
        Season season = new Season();
        season.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setSeasons(new ArrayList<>());
        entities.getSeasons().add(season);

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> seasonRepository.update(season));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void delete_ExistingSeason_Success() throws CustomException {
        UUID id = UUID.randomUUID();

        Season season = new Season();
        season.setId(id);

        Entities entities = new Entities();
        entities.setSeasons(new ArrayList<>());
        entities.getSeasons().add(season);

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> seasonRepository.delete(id));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void addEpisode_ValidDto_Success() throws CustomException, FileNotFoundException {
        UUID seasonId = UUID.randomUUID();
        UUID episodeId = UUID.randomUUID();

        AddEpisodeDto dto = new AddEpisodeDto();
        dto.setSeasonId(seasonId.toString());
        dto.setEpisodeId(episodeId.toString());

        Episode episode = new Episode();
        episode.setId(episodeId);

        Season season = new Season();
        season.setId(seasonId);
        season.setEpisodeIds(new ArrayList<>());

        Entities entities = new Entities();
        entities.setSeasons(new ArrayList<>());
        entities.getSeasons().add(season);

        when(episodeRepository.findById(episodeId)).thenReturn(episode);
        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> seasonRepository.addEpisode(dto));

        Mockito.verify(episodeRepository, times(1)).findById(episodeId);
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void removeEpisode_NonExistingEpisode_ThrowsCustomException() throws CustomException {
        AddEpisodeDto dto = new AddEpisodeDto();
        dto.setSeasonId(UUID.randomUUID().toString());
        dto.setEpisodeId(UUID.randomUUID().toString());

        when(episodeRepository.findById(any())).thenReturn(null);

        Assertions.assertThrows(CustomException.class, () -> seasonRepository.removeEpisode(dto));

        Mockito.verify(episodeRepository, times(1)).findById(any());
        Mockito.verify(repository, never()).saveAll(any());
    }
}
