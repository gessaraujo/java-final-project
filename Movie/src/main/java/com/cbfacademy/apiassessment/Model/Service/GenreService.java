package com.cbfacademy.apiassessment.Model.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import com.cbfacademy.apiassessment.Model.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Genre;
import com.cbfacademy.apiassessment.Model.Repository.GenreRepository;

@Service
public class GenreService {

    @Autowired
    private GenreRepository repository;

    public void create(Genre genre) throws Exception {
        validGenre(genre, true);
        genre.setId(UUID.randomUUID());

        repository.save(genre);
    }

    public List<Genre> findAll() throws CustomException, FileNotFoundException {
        return repository.findAll();
    }

    public void update(Genre genre) throws Exception {
        validGenre(genre, false);
        repository.update(genre);
    }

    public void delete(String id) throws CustomException {
        if (Utils.stringIsNullOrEmpty(id)) {
            throw new CustomException("ID is empty");
        }

        repository.delete(UUID.fromString(id));
    }

    private void validGenre(Genre genre, boolean insersion) throws Exception {
        if (genre.getName() == null || genre.getName().isEmpty()) {
            throw new Exception("Error creating the genre, the name is empty");
        }

        if (!insersion) {
            return;
        }

        List<Genre> genreList = repository.findAll();

        for (Genre savedGenre : genreList) {
            if (savedGenre.getName().equals(genre.getName())) {
                throw new Exception("The name of the genre already exists");
            }
        }
    }
}
