package com.cbfacademy.apiassessment.Service;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Episode;
import com.cbfacademy.apiassessment.Model.Repository.EpisodeRepository;
import com.cbfacademy.apiassessment.Model.Service.EpisodeService;
import org.junit.Assert;
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

import static org.mockito.Mockito.*;

@SpringBootTest
public class EpisodeServiceTest {

    @Mock
    private EpisodeRepository repository;

    @InjectMocks
    private EpisodeService service;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void createTest() throws FileNotFoundException, CustomException {
        Episode episode = new Episode();
        episode.setDuration(120);
        episode.setName("Episode");
        episode.setDescription("Description");

        when(repository.findAll()).thenReturn(Arrays.asList());

        service.create(episode);

        Mockito.verify(repository).save(episode);
    }

    @Test
    public void createWithNameIsEmptyTest() throws FileNotFoundException, CustomException {
        Episode episode = new Episode();
        episode.setDuration(120);
        episode.setName("");
        episode.setDescription("Description");

        when(repository.findAll()).thenReturn(Arrays.asList());

        CustomException exception = Assert.assertThrows(CustomException.class, () -> service.create(episode));
        String expectedMessage = "Name is empty";
        String actualMessage = exception.getMessage();
        assert(expectedMessage.equals(actualMessage));

        verify(repository, never()).save(episode);
    }

    @Test
    public void findAllTest() throws FileNotFoundException, CustomException {
        Episode episode1 = new Episode();
        episode1.setId(UUID.randomUUID());
        episode1.setDuration(120);
        episode1.setName("Episode 1");
        episode1.setDescription("Description 1");

        Episode episode2 = new Episode();
        episode2.setId(UUID.randomUUID());
        episode2.setDuration(150);
        episode2.setName("Episode 2");
        episode2.setDescription("Description 2");

        List<Episode> episodes = Arrays.asList(episode1, episode2);

        when(repository.findAll()).thenReturn(episodes);

        List<Episode> foundEpisodes = service.findAll();

        Assertions.assertEquals(episodes.size(), foundEpisodes.size());
        Assert.assertTrue(foundEpisodes.contains(episode1));
        Assert.assertTrue(foundEpisodes.contains(episode2));
    }

    @Test
    public void updateTest() throws FileNotFoundException, CustomException {
        Episode episode = new Episode();
        episode.setDuration(120);
        episode.setName("Updated Episode");
        episode.setDescription("Updated Description");

        when(repository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertDoesNotThrow(() -> service.update(episode));

        verify(repository).update(episode);
    }

    @Test
    public void deleteTest() throws CustomException {
        String id = UUID.randomUUID().toString();

        Assertions.assertDoesNotThrow(() -> service.delete(id));

        verify(repository).delete(UUID.fromString(id));
    }

    @Test
    public void deleteWithEmptyIdTest() throws CustomException {
        String id = "";

        CustomException exception = Assert.assertThrows(CustomException.class, () -> service.delete(id));
        Assert.assertEquals("ID is empty", exception.getMessage());

        verify(repository, never()).delete(any());
    }

    @Test
    public void updateWithNameIsEmptyTest() throws CustomException {
        Episode episode = new Episode();
        episode.setDuration(120);
        episode.setName("");
        episode.setDescription("Description");

        Assert.assertThrows(CustomException.class, () -> service.update(episode));

        verify(repository, never()).update(any());
    }

    @Test
    public void updateWithZeroDurationTest() throws CustomException {
        Episode episode = new Episode();
        episode.setDuration(0);
        episode.setName("Episode");
        episode.setDescription("Description");

        Assertions.assertDoesNotThrow(() -> service.update(episode));

        verify(repository).update(episode);
    }

    @Test
    public void createWithExistingNameTest() throws FileNotFoundException, CustomException {
        Episode existingEpisode = new Episode();
        existingEpisode.setDuration(120);
        existingEpisode.setName("Existing Episode");
        existingEpisode.setDescription("Description");

        Episode newEpisode = new Episode();
        newEpisode.setDuration(150);
        newEpisode.setName("Existing Episode");
        newEpisode.setDescription("New Description");

        when(repository.findAll()).thenReturn(Collections.singletonList(existingEpisode));

        CustomException exception = Assert.assertThrows(CustomException.class, () -> service.create(newEpisode));
        Assert.assertEquals("This name is already registered", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    public void createWithZeroDurationTest() throws CustomException {
        Episode episode = new Episode();
        episode.setDuration(0);
        episode.setName("Episode");
        episode.setDescription("Description");

        Assert.assertThrows(CustomException.class, () -> service.create(episode));

        verify(repository, never()).save(any());
    }

    @Test
    public void createWithNullEpisodeTest() throws CustomException {
        Episode episode = null;

        Assert.assertThrows(NullPointerException.class, () -> service.create(episode));

        verify(repository, never()).save(any());
    }
}
