package com.cbfacademy.apiassessment.Model.Repository;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Dto.AddEpisodeDto;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import com.cbfacademy.apiassessment.Model.Entity.Episode;
import com.cbfacademy.apiassessment.Model.Entity.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class SeasonRepository {
    @Autowired
    private DataBaseRepository repository;

    @Autowired
    private EpisodeRepository episodeRepository;

    public void save(Season season) throws CustomException, FileNotFoundException {

        Entities entities = repository.getAll();
        entities.getSeasons().add(season);

        repository.saveAll(entities);
    }

    public List<Season> findAll() throws CustomException, FileNotFoundException {
        Entities entities = repository.getAll();

        return entities.getSeasons();
    }

    public void update(Season season) throws CustomException {
        boolean found = false;
        Entities entities = repository.getAll();
        List<Season> savedSeasons = entities.getSeasons();

        for (int idx = 0; idx < savedSeasons.size(); idx++) {
            Season savedSeason = savedSeasons.get(idx);

            if (savedSeason.getId().equals(savedSeason.getId())) {
                found = true;
                savedSeasons.set(idx, season);
            }

            break;
        }

        if (found) {
            entities.setSeasons(savedSeasons);
            repository.saveAll(entities);
        } else {
            throw new CustomException("ID season not found");
        }
    }

    public void delete(UUID id) throws CustomException {
        boolean found = false;
        Entities entities = repository.getAll();
        List<Season> savedSeasons = entities.getSeasons();

        for (int idx = 0; idx < savedSeasons.size(); idx++) {
            Season savedSeason = savedSeasons.get(idx);

            if (savedSeason.getId().equals(id)) {
                found = true;
                savedSeasons.remove(idx);
            }

            break;
        }

        if (found) {
            entities.setSeasons(savedSeasons);
            repository.saveAll(entities);
        } else {
            throw new CustomException("ID season not found");
        }
    }

    public Season findById(UUID id) throws CustomException {
        Entities entities = repository.getAll();
        List<Season> savedSeasons = entities.getSeasons();

        if (savedSeasons != null) {
            for (Season season : savedSeasons) {
                if (season.getId().equals(id)) {
                    return season;
                }
            }
        }

        return null;
    }

    public void addEpisode(AddEpisodeDto dto) throws CustomException, FileNotFoundException {
        Episode episode = episodeRepository.findById(UUID.fromString(dto.getEpisodeId()));

        if (episode == null) {
            throw new CustomException("ID episode not found");
        }

        Season season = findById(UUID.fromString(dto.getSeasonId()));

        if (episode == null) {
            throw new CustomException("ID season not found");
        }

        if (season.getEpisodeIds() == null) {
            season.setEpisodeIds(new ArrayList<>());
        }

        season.getEpisodeIds().add(UUID.fromString(dto.getEpisodeId()));

        update(season);
    }

    public void removeEpisode(AddEpisodeDto dto) throws CustomException, FileNotFoundException {
        Episode episode = episodeRepository.findById(UUID.fromString(dto.getEpisodeId()));

        if (episode == null) {
            throw new CustomException("ID episode not found");
        }

        Season season = findById(UUID.fromString(dto.getSeasonId()));

        if (episode == null) {
            throw new CustomException("ID season not found");
        }

        if (season.getEpisodeIds() == null) {
            throw new CustomException("There are no episodes to be removed");
        }

        season.getEpisodeIds().remove(UUID.fromString(dto.getEpisodeId()));

        update(season);
    }
}
