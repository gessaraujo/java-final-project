package com.cbfacademy.apiassessment.Controller;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Dto.AddEpisodeDto;
import com.cbfacademy.apiassessment.Model.Entity.Season;
import com.cbfacademy.apiassessment.Model.Service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@Controller
@RequestMapping(value = "season")
public class SeasonController {
    @Autowired
    private SeasonService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Season season) throws FileNotFoundException, CustomException {
        service.create(season);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Season>> findAll() throws FileNotFoundException, CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Season season) throws CustomException {
        service.update(season);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody String id) throws CustomException {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/addEpisode")
    public ResponseEntity<Void> addEpisode(@RequestBody AddEpisodeDto dto) throws FileNotFoundException, CustomException {
        service.addEpisode(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/removeEpisode")
    public ResponseEntity<Void> removeEpisode(@RequestBody AddEpisodeDto dto) throws FileNotFoundException, CustomException {
        service.removeEpisode(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
