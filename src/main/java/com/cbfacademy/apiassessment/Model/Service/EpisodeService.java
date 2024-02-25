package com.cbfacademy.apiassessment.Model.Service;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import com.cbfacademy.apiassessment.Model.Entity.Episode;
import com.cbfacademy.apiassessment.Model.Repository.EpisodeRepository;
import com.cbfacademy.apiassessment.Model.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class EpisodeService {
    @Autowired
    private EpisodeRepository repository;

    public void create(Episode episode) throws CustomException, FileNotFoundException {
        validateEpisode(episode, true);

        episode.setId(UUID.randomUUID());
        repository.save(episode);
    }

    public List<Episode> findAll() throws FileNotFoundException, CustomException {
        return repository.findAll();
    }

    public void update(Episode episode) throws FileNotFoundException, CustomException {
        validateEpisode(episode, false);
        repository.update(episode);
    }

    public void delete(String id) throws CustomException {
        if (Utils.stringIsNullOrEmpty(id)) {
            throw new CustomException("ID is empty");
        }

        repository.delete(UUID.fromString(id));
    }

    private void validateEpisode(Episode episode, boolean insersion) throws CustomException, FileNotFoundException {
        if (Utils.stringIsNullOrEmpty(episode.getName())) {
            throw new CustomException("Name is empty");
        }

        if (!insersion) {
            return;
        }

        List<Episode> episodes = repository.findAll();

        for (Episode savedEpisode : episodes) {
            if (savedEpisode.getName().equals(episode.getName())) {
                throw new CustomException("This name is already registered");
            }
        }

        if (episode.getDuration() == 0) {
            throw new CustomException("Duration is zero");
        }
    }
}
