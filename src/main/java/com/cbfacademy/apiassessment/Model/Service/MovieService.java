package com.cbfacademy.apiassessment.Model.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Repository.Entities;
import com.cbfacademy.apiassessment.Model.Repository.GenreRepository;
import com.cbfacademy.apiassessment.Model.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import com.cbfacademy.apiassessment.Model.Repository.MovieRepository;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    public void create(Movie movie) throws CustomException, FileNotFoundException {
        validateMovie(movie, true);
        movie.setId(UUID.randomUUID());

        repository.save(movie);
    }

    public List<Movie> findAll() throws FileNotFoundException, CustomException {
        return repository.findAll();
    }

    public void update(Movie movie) throws FileNotFoundException, CustomException {
        validateMovie(movie, false);
        repository.update(movie);
    }

    public void delete(String id) throws CustomException {
        if (Utils.stringIsNullOrEmpty(id)) {
            throw new CustomException("ID is empty");
        }

        repository.delete(UUID.fromString(id));
    }

    private void validateMovie(Movie movie, boolean insersion) throws CustomException, FileNotFoundException {
        if (Utils.stringIsNullOrEmpty(movie.getTitle())) {
            throw new CustomException("Title is empty");
        }

        if (movie.getGenre() != null) {
            if (genreRepository.findById(movie.getGenre().getId()) == null) {
                throw new CustomException("The informed genre does not exist");
            }
        }

        if (!insersion) {
            return;
        }

        List<Movie> movies = repository.findAll();

        for (Movie savedMovie : movies) {
            if (savedMovie.getTitle().equals(movie.getTitle())) {
                throw new CustomException("This title is already registered");
            }
        }
    }
}
