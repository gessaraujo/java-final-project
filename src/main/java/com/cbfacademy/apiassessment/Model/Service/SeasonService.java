package com.cbfacademy.apiassessment.Model.Service;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Dto.AddEpisodeDto;
import com.cbfacademy.apiassessment.Model.Entity.Season;
import com.cbfacademy.apiassessment.Model.Repository.EpisodeRepository;
import com.cbfacademy.apiassessment.Model.Repository.SeasonRepository;
import com.cbfacademy.apiassessment.Model.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SeasonService {
    @Autowired
    private SeasonRepository repository;

    @Autowired
    private EpisodeRepository episodeRepository;

    public void create(Season season) throws FileNotFoundException, CustomException {
        validateSeason(season);
        season.setId(UUID.randomUUID());
        repository.save(season);
    }

    public List<Season> findAll() throws FileNotFoundException, CustomException {
        return repository.findAll();
    }

    public void update(Season season) throws CustomException {
        validateSeason(season);
        repository.update(season);
    }

    public void delete(String id) throws CustomException {
        if (Utils.stringIsNullOrEmpty(id)) {
            throw new CustomException("ID is empty");
        }

        repository.delete(UUID.fromString(id));
    }

    public void addEpisode(AddEpisodeDto dto) throws CustomException, FileNotFoundException {
        validateAddEpisode(dto);
        repository.addEpisode(dto);
    }

    public void removeEpisode(AddEpisodeDto dto) throws CustomException, FileNotFoundException {
        validateRemoveEpisode(dto);
        repository.removeEpisode(dto);
    }

    private void validateSeason(Season season) throws CustomException {
        if (season.getEpisodeIds() == null) {
            season.setEpisodeIds(new ArrayList<>());
        }

        for (UUID episodeId : season.getEpisodeIds()) {
            if (episodeRepository.findById(episodeId) == null) {
                throw new CustomException("ID episode not found");
            }
        }
    }

    private void validateAddEpisode(AddEpisodeDto dto) throws CustomException {
        if (Utils.stringIsNullOrEmpty(dto.getSeasonId())) {
            throw new CustomException("ID season not found");
        }

        if (Utils.stringIsNullOrEmpty(dto.getEpisodeId())) {
            throw new CustomException("ID episode not found");
        }

        Season season = repository.findById(UUID.fromString(dto.getSeasonId()));

        if (season != null) {
            for (UUID episodeId : season.getEpisodeIds()) {
                if (episodeId.toString().equals(dto.getEpisodeId())) {
                    throw new CustomException("Episode has already been added");
                }
            }
        }
    }

    private void validateRemoveEpisode(AddEpisodeDto dto) throws CustomException {
        if (Utils.stringIsNullOrEmpty(dto.getSeasonId())) {
            throw new CustomException("ID season not found");
        }

        if (Utils.stringIsNullOrEmpty(dto.getEpisodeId())) {
            throw new CustomException("ID episode not found");
        }
    }
}
