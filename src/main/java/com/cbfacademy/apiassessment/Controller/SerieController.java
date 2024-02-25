package com.cbfacademy.apiassessment.Controller;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Dto.AddEpisodeDto;
import com.cbfacademy.apiassessment.Model.Dto.AddSeasonDto;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import com.cbfacademy.apiassessment.Model.Entity.Content.Serie;
import com.cbfacademy.apiassessment.Model.Service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "serie")
public class SerieController {
    @Autowired
    private SerieService service;
    @PostMapping()
    public void create(@RequestBody Serie serie) throws Exception {
        service.create(serie);
    }

    @GetMapping()
    public List<Serie> findAll() throws FileNotFoundException, CustomException {
        return service.findAll();
    }

    @PutMapping()
    public void updtade(@RequestBody Serie serie) throws CustomException, FileNotFoundException {
        service.update(serie);
    }

    @DeleteMapping()
    public void delete(@RequestBody String id) throws CustomException {
        service.delete(id);
    }

    @PostMapping(value = "/addSeason")
    public void addESeason(@RequestBody AddSeasonDto dto) throws FileNotFoundException, CustomException {
        service.addSeason(dto);
    }

    @PostMapping(value = "/removeSeason")
    public void removeSeason(@RequestBody AddSeasonDto dto) throws FileNotFoundException, CustomException {
        service.removeSeason(dto);
    }
}
