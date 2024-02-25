package com.cbfacademy.apiassessment.Controller;

import java.io.FileNotFoundException;
import java.util.List;

import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Genre;
import com.cbfacademy.apiassessment.Model.Service.GenreService;

@RestController
@RequestMapping(value = "genre")
public class GenreController {
    
    @Autowired
    private GenreService genreService;

    @PostMapping
    public void create(@RequestBody Genre genre) throws Exception {
        genreService.create(genre);
    }

    @GetMapping
    public List<Genre> findAll() throws CustomException, FileNotFoundException {
        return genreService.findAll();
    }

    @PutMapping()
    public void updtade(@RequestBody Genre genre) throws Exception {
        genreService.update(genre);
    }

    @DeleteMapping()
    public void delete(@RequestBody String id) throws CustomException {
        genreService.delete(id);
    }
}
