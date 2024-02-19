package com.cbfacademy.apiassessment.Model.Repository;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Genre;

@Repository
public class GenreRepository {

    @Autowired
    DataBaseRepository repository;

    public void save(Genre genre) throws CustomException, FileNotFoundException {
        
        Entities entities = repository.getAll();
        entities.getGenres().add(genre);

        repository.saveAll(entities);
    }

    public List<Genre> findAll() throws CustomException, FileNotFoundException {
        Entities entities = repository.getAll();

        return entities.getGenres();
    }

    public void update(Genre genre) throws CustomException {
        boolean found = false;
        Entities entities = repository.getAll();
        List<Genre> savedGenres = entities.getGenres();

        for (int idx = 0; idx < savedGenres.size(); idx++) {
            Genre savedGenre = savedGenres.get(idx);

            if (savedGenre.getId().equals(genre.getId())) {
                found = true;
                savedGenres.set(idx, genre);
            }

            break;
        }

        if (found) {
            entities.setGenres(savedGenres);
            repository.saveAll(entities);
        } else {
            throw new CustomException("ID genre not found");
        }
    }

    public void delete(UUID id) throws CustomException {
        boolean found = false;
        Entities entities = repository.getAll();
        List<Genre> savedGenres = entities.getGenres();

        for (int idx = 0; idx < savedGenres.size(); idx++) {
            Genre savedGenre = savedGenres.get(idx);

            if (savedGenre.getId().equals(id)) {
                found = true;
                savedGenres.remove(idx);
            }

            break;
        }

        if (found) {
            entities.setGenres(savedGenres);
            repository.saveAll(entities);
        } else {
            throw new CustomException("ID Genre not found");
        }
    }
}
