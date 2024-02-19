package com.cbfacademy.apiassessment.Controller;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import com.cbfacademy.apiassessment.Model.Service.MovieService;

import java.io.FileNotFoundException;
import java.util.List;


@RestController
@RequestMapping(value = "movie")
public class MovieController {
    @Autowired
    private MovieService service;

    @PostMapping()
    public void create(@RequestBody Movie movie) throws Exception {
        service.create(movie);
    }

    @GetMapping()
    public List<Movie> findAll() throws FileNotFoundException, CustomException {
        return service.findAll();
    }

    @PutMapping()
    public void updtade(@RequestBody Movie movie) throws CustomException, FileNotFoundException {
        service.update(movie);
    }

    @DeleteMapping()
    public void delete(@RequestBody String id) throws CustomException {
        service.delete(id);
    }
}
