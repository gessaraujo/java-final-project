package com.cbfacademy.apiassessment.Model.Repository;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Dto.AddEpisodeDto;
import com.cbfacademy.apiassessment.Model.Dto.AddSeasonDto;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import com.cbfacademy.apiassessment.Model.Entity.Content.Serie;
import com.cbfacademy.apiassessment.Model.Entity.Episode;
import com.cbfacademy.apiassessment.Model.Entity.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class SerieRepository {
    @Autowired
    private DataBaseRepository repository;

    @Autowired
    private SeasonRepository seasonRepository;

    public void save(Serie serie) throws CustomException, FileNotFoundException {

        Entities entities = repository.getAll();
        entities.getSeries().add(serie);

        repository.saveAll(entities);
    }

    public List<Serie> findAll() throws CustomException, FileNotFoundException {
        Entities entities = repository.getAll();

        return entities.getSeries();
    }

    public void update(Serie serie) throws CustomException {
        boolean found = false;
        Entities entities = repository.getAll();
        List<Serie> savedSeries = entities.getSeries();

        for (int idx = 0; idx < savedSeries.size(); idx++) {
            Serie savedSerie = savedSeries.get(idx);

            if (savedSerie.getId().equals(serie.getId())) {
                found = true;
                savedSeries.set(idx, serie);
            }

            break;
        }

        if (found) {
            entities.setSeries(savedSeries);
            repository.saveAll(entities);
        } else {
            throw new CustomException("ID serie not found");
        }
    }

    public void delete(UUID id) throws CustomException {
        boolean found = false;
        Entities entities = repository.getAll();
        List<Serie> savedSeries = entities.getSeries();

        for (int idx = 0; idx < savedSeries.size(); idx++) {
            Serie savedSerie = savedSeries.get(idx);

            if (savedSerie.getId().equals(id)) {
                found = true;
                savedSeries.remove(idx);
            }

            break;
        }

        if (found) {
            entities.setSeries(savedSeries);
            repository.saveAll(entities);
        } else {
            throw new CustomException("ID serie not found");
        }
    }

    public Serie findById(UUID id) throws CustomException, FileNotFoundException {
        Entities entities = repository.getAll();
        List<Serie> series = entities.getSeries();

        if (series != null) {
            for (Serie serie : series) {
                if (serie.getId().equals(id)) {
                    return serie;
                }
            }
        }

        throw new CustomException("ID serie not found");
    }

    public void addSeason(AddSeasonDto dto) throws CustomException, FileNotFoundException {
        Season season = seasonRepository.findById(UUID.fromString(dto.getSeasonId()));

        if (season == null) {
            throw new CustomException("ID season not found");
        }

        Serie serie = findById(UUID.fromString(dto.getSerieId()));

        if (serie == null) {
            throw new CustomException("ID serie not found");
        }

        if (serie.getSeasonIds() == null) {
            serie.setSeasonIds(new ArrayList<>());
        }

        serie.getSeasonIds().add(UUID.fromString(dto.getSeasonId()));

        update(serie);
    }

    public void removeSeason(AddSeasonDto dto) throws CustomException, FileNotFoundException {
        Season season = seasonRepository.findById(UUID.fromString(dto.getSeasonId()));

        if (season == null) {
            throw new CustomException("ID season not found");
        }

        Serie serie = findById(UUID.fromString(dto.getSerieId()));

        if (serie == null) {
            throw new CustomException("ID serie not found");
        }

        if (serie.getSeasonIds() == null) {
            throw new CustomException("There are no seasons to be removed");
        }

        serie.getSeasonIds().remove(UUID.fromString(dto.getSeasonId()));

        update(serie);
    }
}
