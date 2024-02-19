package com.cbfacademy.apiassessment.Model.Repository;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

import com.cbfacademy.apiassessment.Model.Entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {
    @Autowired
    DataBaseRepository repository;

    public void save(Movie movie) throws CustomException, FileNotFoundException {
        
        Entities entities = repository.getAll();
        entities.getMovies().add(movie);

        repository.saveAll(entities);
    }

    public List<Movie> findAll() throws CustomException, FileNotFoundException {
        Entities entities = repository.getAll();

        return entities.getMovies();
    }

    public void update(Movie movie) throws CustomException {
        boolean found = false;
        Entities entities = repository.getAll();
        List<Movie> savedMovies = entities.getMovies();

        for (int idx = 0; idx < savedMovies.size(); idx++) {
            Movie savedMovie = savedMovies.get(idx);

            if (savedMovie.getId().equals(movie.getId())) {
                found = true;
                savedMovies.set(idx, movie);
            }

            break;
        }

        if (found) {
            entities.setMovies(savedMovies);
            repository.saveAll(entities);
        } else {
            throw new CustomException("ID movie not found");
        }
    }

    public void delete(UUID id) throws CustomException {
        boolean found = false;
        Entities entities = repository.getAll();
        List<Movie> savedMovies = entities.getMovies();

        for (int idx = 0; idx < savedMovies.size(); idx++) {
            Movie savedMovie = savedMovies.get(idx);

            if (savedMovie.getId().equals(id)) {
                found = true;
                savedMovies.remove(idx);
            }

            break;
        }

        if (found) {
            entities.setMovies(savedMovies);
            repository.saveAll(entities);
        } else {
            throw new CustomException("ID movie not found");
        }
    }

    public Movie findById(UUID id) throws CustomException, FileNotFoundException {
        Entities entities = repository.getAll();

        for (Movie movie : entities.getMovies()) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }

        throw new CustomException("ID movie not found");
    }
}
