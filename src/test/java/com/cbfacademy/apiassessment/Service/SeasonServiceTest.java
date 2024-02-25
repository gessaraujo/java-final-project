package com.cbfacademy.apiassessment.Service;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Dto.AddEpisodeDto;
import com.cbfacademy.apiassessment.Model.Entity.Episode;
import com.cbfacademy.apiassessment.Model.Entity.Season;
import com.cbfacademy.apiassessment.Model.Repository.EpisodeRepository;
import com.cbfacademy.apiassessment.Model.Repository.SeasonRepository;
import com.cbfacademy.apiassessment.Model.Service.SeasonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@SpringBootTest
public class SeasonServiceTest {
    @Mock
    private SeasonRepository seasonRepository;

    @Mock
    private EpisodeRepository episodeRepository;

    @InjectMocks
    private SeasonService seasonService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void createSeason_ValidSeason_Success() throws FileNotFoundException, CustomException {
        Season season = new Season();
        season.setEpisodeIds(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));

        Episode episode = new Episode();

        when(episodeRepository.findById(Mockito.any())).thenReturn(episode);

        Assertions.assertDoesNotThrow(() -> seasonService.create(season));
    }

    @Test
    public void createSeason_NullEpisodeIds_Success() throws FileNotFoundException, CustomException {
        Season season = new Season();
        season.setEpisodeIds(null);

        Assertions.assertDoesNotThrow(() -> seasonService.create(season));

        Mockito.verify(seasonRepository, times(1)).save(season);
    }

    @Test
    public void findAll_ReturnsListOfSeasons() throws FileNotFoundException, CustomException {
        List<Season> seasons = Arrays.asList(new Season(), new Season());

        when(seasonRepository.findAll()).thenReturn(seasons);

        Assertions.assertEquals(seasons, seasonService.findAll());
    }

    @Test
    public void updateSeason_ValidSeason_Success() throws CustomException {
        Season season = new Season();
        season.setEpisodeIds(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));

        Assertions.assertThrows(CustomException.class, () -> seasonService.update(season));
    }

    @Test
    public void deleteSeason_ValidId_Success() throws CustomException {
        String id = UUID.randomUUID().toString();

        Assertions.assertDoesNotThrow(() -> seasonService.delete(id));

        Mockito.verify(seasonRepository, times(1)).delete(UUID.fromString(id));
    }

    @Test
    public void addEpisode_ValidDto_Success() throws CustomException, FileNotFoundException {
        AddEpisodeDto dto = new AddEpisodeDto();
        dto.setSeasonId(UUID.randomUUID().toString());
        dto.setEpisodeId(UUID.randomUUID().toString());

        Assertions.assertDoesNotThrow(() -> seasonService.addEpisode(dto));

        Mockito.verify(seasonRepository, times(1)).addEpisode(dto);
    }

    @Test
    public void removeEpisode_ValidDto_Success() throws CustomException, FileNotFoundException {
        AddEpisodeDto dto = new AddEpisodeDto();
        dto.setSeasonId(UUID.randomUUID().toString());
        dto.setEpisodeId(UUID.randomUUID().toString());

        Assertions.assertDoesNotThrow(() -> seasonService.removeEpisode(dto));

        Mockito.verify(seasonRepository, times(1)).removeEpisode(dto);
    }

    @Test
    public void validateSeason_InvalidEpisodeId_ThrowsCustomException() throws CustomException {
        Season season = new Season();
        season.setEpisodeIds(Collections.singletonList(UUID.randomUUID()));

        when(episodeRepository.findById(any(UUID.class))).thenReturn(null);

        Assertions.assertThrows(CustomException.class, () -> seasonService.create(season));
    }

    @Test
    public void validateAddEpisode_EmptySeasonId_ThrowsCustomException() {
        AddEpisodeDto dto = new AddEpisodeDto();
        dto.setEpisodeId(UUID.randomUUID().toString());

        Assertions.assertThrows(CustomException.class, () -> seasonService.addEpisode(dto));
    }

    @Test
    public void validateAddEpisode_EmptyEpisodeId_ThrowsCustomException() {
        AddEpisodeDto dto = new AddEpisodeDto();
        dto.setSeasonId(UUID.randomUUID().toString());

        Assertions.assertThrows(CustomException.class, () -> seasonService.addEpisode(dto));
    }

    @Test
    public void validateRemoveEpisode_EmptySeasonId_ThrowsCustomException() {
        AddEpisodeDto dto = new AddEpisodeDto();
        dto.setEpisodeId(UUID.randomUUID().toString());

        Assertions.assertThrows(CustomException.class, () -> seasonService.removeEpisode(dto));
    }

    @Test
    public void validateRemoveEpisode_EmptyEpisodeId_ThrowsCustomException() {
        AddEpisodeDto dto = new AddEpisodeDto();
        dto.setSeasonId(UUID.randomUUID().toString());

        Assertions.assertThrows(CustomException.class, () -> seasonService.removeEpisode(dto));
    }
}
