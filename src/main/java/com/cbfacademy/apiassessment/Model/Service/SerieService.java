package com.cbfacademy.apiassessment.Model.Service;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Dto.AddEpisodeDto;
import com.cbfacademy.apiassessment.Model.Dto.AddSeasonDto;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import com.cbfacademy.apiassessment.Model.Entity.Content.Serie;
import com.cbfacademy.apiassessment.Model.Entity.Season;
import com.cbfacademy.apiassessment.Model.Repository.GenreRepository;
import com.cbfacademy.apiassessment.Model.Repository.SerieRepository;
import com.cbfacademy.apiassessment.Model.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    public void create(Serie serie) throws FileNotFoundException, CustomException {
        validateSerie(serie, true);
        serie.setId(UUID.randomUUID());

        repository.save(serie);
    }

    public List<Serie> findAll() throws FileNotFoundException, CustomException {
        return repository.findAll();
    }

    public void update(Serie serie) throws FileNotFoundException, CustomException {
        validateSerie(serie, false);
        repository.update(serie);
    }

    public void delete(String id) throws CustomException {
        if (Utils.stringIsNullOrEmpty(id)) {
            throw new CustomException("ID is empty");
        }

        repository.delete(UUID.fromString(id));
    }

    public void addSeason(AddSeasonDto dto) throws FileNotFoundException, CustomException {
        validateAddSeason(dto);
        repository.addSeason(dto);
    }

    public void removeSeason(AddSeasonDto dto) throws FileNotFoundException, CustomException {
        validateRemoveSeason(dto);
        repository.removeSeason(dto);
    }

    private void validateSerie(Serie serie, boolean insersion) throws CustomException, FileNotFoundException {
        if (Utils.stringIsNullOrEmpty(serie.getTitle())) {
            throw new CustomException("Title is empty");
        }

        if (serie.getGenre() != null) {
            if (genreRepository.findById(serie.getGenre().getId()) == null) {
                throw new CustomException("The informed genre does not exist");
            }
        }

        if (!insersion) {
            return;
        }

        List<Serie> series = repository.findAll();

        for (Serie savedSerie : series) {
            if (savedSerie.getTitle().equals(serie.getTitle())) {
                throw new CustomException("This title is already registered");
            }
        }
    }

    private void validateAddSeason(AddSeasonDto dto) throws CustomException, FileNotFoundException {
        if (Utils.stringIsNullOrEmpty(dto.getSeasonId())) {
            throw new CustomException("ID season not found");
        }

        if (Utils.stringIsNullOrEmpty(dto.getSerieId())) {
            throw new CustomException("ID serie not found");
        }

        Serie serie = repository.findById(UUID.fromString(dto.getSerieId()));

        if (serie != null) {
            List<UUID> seasons = serie.getSeasonIds();

            if (seasons != null) {
                for (UUID seasonId : serie.getSeasonIds()) {
                    if (seasonId.toString().equals(dto.getSeasonId())) {
                        throw new CustomException("Season has already been added");
                    }
                }
            }
        }
    }

    private void validateRemoveSeason(AddSeasonDto dto) throws CustomException {
        if (Utils.stringIsNullOrEmpty(dto.getSeasonId())) {
            throw new CustomException("ID season not found");
        }

        if (Utils.stringIsNullOrEmpty(dto.getSerieId())) {
            throw new CustomException("ID serie not found");
        }
    }
}
