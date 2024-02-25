package com.cbfacademy.apiassessment.Repository;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Episode;
import com.cbfacademy.apiassessment.Model.Repository.DataBaseRepository;
import com.cbfacademy.apiassessment.Model.Repository.Entities;
import com.cbfacademy.apiassessment.Model.Repository.EpisodeRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EpisodeRepositoryTesst {
    @Mock
    private DataBaseRepository repository;

    @InjectMocks
    private EpisodeRepository episodeRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void save_ValidEpisode_Success() throws CustomException {
        Entities entities = new Entities();
        entities.setEpisodes(new ArrayList<>());

        Episode episode = new Episode();
        episode.setId(UUID.randomUUID());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> episodeRepository.save(episode));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(any());
    }

    @Test
    public void findAll_ReturnsListOfEpisodes() throws CustomException, FileNotFoundException {
        Entities entities = new Entities();
        entities.setEpisodes(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        List<Episode> episodes = episodeRepository.findAll();

        Assertions.assertNotNull(episodes);
        Assertions.assertEquals(0, episodes.size());
    }

    @Test
    public void update_ExistingEpisode_Success() throws CustomException {
        Episode episode = new Episode();
        episode.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setEpisodes(new ArrayList<>());
        entities.getEpisodes().add(episode);

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> episodeRepository.update(episode));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void delete_ExistingEpisode_Success() throws CustomException {
        UUID id = UUID.randomUUID();

        Episode episode = new Episode();
        episode.setId(id);

        Entities entities = new Entities();
        entities.setEpisodes(new ArrayList<>());
        entities.getEpisodes().add(episode);

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> episodeRepository.delete(id));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void findById_ExistingId_ReturnsEpisode() throws CustomException {
        UUID id = UUID.randomUUID();

        Episode episode = new Episode();
        episode.setId(id);

        Entities entities = new Entities();
        entities.setEpisodes(new ArrayList<>());
        entities.getEpisodes().add(episode);

        when(repository.getAll()).thenReturn(entities);

        Episode foundEpisode = Assertions.assertDoesNotThrow(() -> episodeRepository.findById(id));

        Assertions.assertNotNull(foundEpisode);
        Assertions.assertEquals(id, foundEpisode.getId());
    }

    @Test
    public void findById_NonExistingId_ReturnsNull() throws CustomException {
        UUID id = UUID.randomUUID();

        Entities entities = new Entities();
        entities.setEpisodes(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Episode foundEpisode = Assertions.assertDoesNotThrow(() -> episodeRepository.findById(id));

        Assertions.assertNull(foundEpisode);
    }

    @Test
    public void update_NonExistingEpisode_ThrowsCustomException() throws CustomException {
        Episode episode = new Episode();
        episode.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setEpisodes(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertThrows(CustomException.class, () -> episodeRepository.update(episode));
    }

    @Test
    public void delete_NonExistingEpisode_ThrowsCustomException() throws CustomException {
        UUID id = UUID.randomUUID();

        Entities entities = new Entities();
        entities.setEpisodes(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertThrows(CustomException.class, () -> episodeRepository.delete(id));
    }

    @Test
    public void findById_ExceptionInRepository_ThrowsCustomException() throws CustomException {
        when(repository.getAll()).thenThrow(CustomException.class);

        Assertions.assertThrows(CustomException.class, () -> episodeRepository.findById(UUID.randomUUID()));
    }
}
