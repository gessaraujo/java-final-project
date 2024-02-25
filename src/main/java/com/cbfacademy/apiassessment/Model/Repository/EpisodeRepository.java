package com.cbfacademy.apiassessment.Model.Repository;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Episode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@Repository
public class EpisodeRepository {
    @Autowired
    private DataBaseRepository repository;

    public void save(Episode episode) throws CustomException {
        Entities entities = repository.getAll();
        entities.getEpisodes().add(episode);

        repository.saveAll(entities);
    }

    public List<Episode> findAll() throws CustomException, FileNotFoundException {
        Entities entities = repository.getAll();

        return entities.getEpisodes();
    }

    public void update(Episode episode) throws CustomException {
        boolean found = false;
        Entities entities = repository.getAll();
        List<Episode> savedEpisodes = entities.getEpisodes();

        for (int idx = 0; idx < savedEpisodes.size(); idx++) {
            Episode savedEpisode = savedEpisodes.get(idx);

            if (savedEpisode.getId().equals(episode.getId())) {
                found = true;
                savedEpisodes.set(idx, episode);
            }

            break;
        }

        if (found) {
            entities.setEpisodes(savedEpisodes);
            repository.saveAll(entities);
        } else {
            throw new CustomException("ID episode not found");
        }
    }

    public void delete(UUID id) throws CustomException {
        boolean found = false;
        Entities entities = repository.getAll();
        List<Episode> savedEpisodes = entities.getEpisodes();

        for (int idx = 0; idx < savedEpisodes.size(); idx++) {
            Episode savedEpisode = savedEpisodes.get(idx);

            if (savedEpisode.getId().equals(id)) {
                found = true;
                savedEpisodes.remove(idx);
            }

            break;
        }

        if (found) {
            entities.setEpisodes(savedEpisodes);
            repository.saveAll(entities);
        } else {
            throw new CustomException("ID episode not found");
        }
    }

    public Episode findById(UUID id) throws CustomException {
        Entities entities = repository.getAll();
        List<Episode> savedEpisodes = entities.getEpisodes();

        if (savedEpisodes != null) {
            for (Episode episode : savedEpisodes) {
                if (episode.getId().equals(id)) {
                    return episode;
                }
            }
        }

        return null;
    }
}
